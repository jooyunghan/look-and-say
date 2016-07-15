
function read(resume) {
    return {
        action: 'read',
        resume
    }
}

function write(output, resume) {
    return {
        action: 'write',
        output,
        resume
    }
}

function terminate() {
    return {
        action: 'terminate'
    }
}


function init(resume, state, input) {
    switch (resume) {
        case 'init':
            return write(1, 'terminate');
        case 'terminate':
            return terminate();
        default:
            throw new Error('unknown position: ' + resume);
    }
}

function next(resume, state, input) {
    switch (resume) {
        case 'init':
            return read('read0');
        case 'read0':
            state.prev = input;
            state.count = 1;
            return read('read1');
        case 'read1':
            if (input === 0) {
                return write(state.count, 'write2');
            } else if (state.prev == input) {
                state.count++;
                return read('read1');
            } else {
                state.next = input;
                return write(state.count, 'write0');
            }
        case 'write0':
            return write(state.prev, 'write1');
        case 'write1':
            state.prev = state.next;
            state.count = 1;
            return read('read1');
        case 'write2':
            return write(state.prev, 'terminate');
        case 'terminate':
            return terminate();
        default:
            throw new Error('unknown position: ' + resume);
    }
}

function take(n) {
    return (resume, state, input) => {
        switch (resume) {
            case 'init':
                return read('read');
            case 'read':
                if (input == 0)
                    return terminate();
                else if (--n > 0)
                    return write(input, 'init');
                else
                    return write(input, 'terminate');
            case 'terminate':
                return terminate();
            default:
                throw new Error('unknown position: ' + resume);
        }
    }
}

function nth(n) {
    let count = 0;
    return (resume, state, input) => {
        switch (resume) {
            case 'init':
                return read('read');
            case 'read':
                if (input == 0)
                    throw new Error(count + ' read, but expected ' + n);
                else if (count == n)
                    return write(input, 'terminate');
                else {
                    count++;
                    return read('read');
                }
            case 'terminate':
                return terminate();
            default:
                throw new Error('unknown position: ' + resume);
        }
    }
}

let written = [];
let toRead = [];
let maxWritten = 10;

function emit(action, n, value) {
    written[n] = written[n] || 0; // init
    toRead[n] = toRead[n] || false;
    if (action === 'write') {
        written[n]++;
        if (maxWritten < written[n]) {
            maxWritten = written[n]
        }
        toRead[n] = false;
    } else if (action == 'read') {
        toRead[n] = true;
    } else {
        toRead[n] = false;
    }
    return draw(action, n, value);
}

let canvas = document.getElementById('canvas');
let ctx = canvas.getContext('2d');

function draw(action, n, value) {
    let dx = canvas.height / written.length;
    let s = Math.max(dx, 1);
    let x, y;
    //console.log(W,H);
    if (action == 'read') {
        y = dx * n ;
        x = dx * written[n];
        // ctx.fillStyle = "rgb(200, 200, 200)";
        // ctx.fillRect(x - dx, y, dx, dx);
        ctx.fillStyle = "red";
        ctx.fillRect(x, y, dx, dx);
    } else if (action == 'write') {
        y = dx * n ;
        x = dx * (written[n] - 1);
        // ctx.fillStyle = "rgb(200, 200, 200)";
        // ctx.fillRect(x - dx, y, dx, dx);
        ctx.fillStyle = color(value);
        ctx.fillRect(x, y, s, s);
    } else {
        y = dx * n ;
        x = dx * written[n];
        ctx.fillStyle = "black";
        ctx.fillRect(x, y, s, s);
    }
    return x > canvas.width;
}

function color(value) {
    //return 'silver'
     return (value == 1) ? 'rgb(200,200,200)'
             : (value == 2) ? 'rgb(150,150,150)'
             : 'rgb(100,100,100)' ;
}

let processes = [];
processes.push(init);
for (let i = 0; i < 50; i++) {
    processes.push(next);
}

let n = processes.length - 1;
let stack = processes.map(() => ({ resume: 'init', state: {}, input: undefined, terminated: false }));

function run() {
    //console.log(n, 'resume', stack[n]);
    let result = processes[n](stack[n].resume, stack[n].state, stack[n].input);
    //console.log(n, 'result', result, stack[n].state)
    stack[n].resume = result.resume;
    switch (result.action) {
        case 'write':
            if (n + 1 < processes.length) {
                let filled = emit('write', n, result.output);
                if (filled) {
                    console.log("filled", n, processes.length)
                } else {
                    stack[n + 1].input = result.output;
                    //console.log(n, ">>>>", result.output);
                    n++;
                }
            } else {
                //console.log(result.output);
            }
            break;
        case 'read':
            if (n > 0 && !stack[n - 1].terminated) {
                emit('read', n - 1);
                n--;
            } else {
                throw new Error('cannot read');
            }
            break;
        case 'terminate':
            stack[n].terminated = true;
            if (n == processes.length - 1) {
                return;
            } else {
                emit('terminate', n);
                stack[n + 1].input = 0;
                n++;
            }
            break;
        default:
            throw new Error('unknown action: ' + result.action);
    }
    window.requestAnimationFrame(run);
   // setTimeout(run, 100);
}

run();