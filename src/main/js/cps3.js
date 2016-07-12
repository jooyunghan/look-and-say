
function Pure(value) {
  this.value = value
}
Pure.prototype = {
  map(f) {
    return new Pure(f(this.value))
  },
  flatMap(f) {
    return f(this.value)
  }
}
function Read(cont) {
  this.cont = cont // cont :: String -> Read<A>
}
Read.prototype = {
  map(f) {
    return new Read(s => this.cont(s).map(f))
  },
  flatMap(f) { // f :: a -> Read<B>
    return new Read(x => this.cont(x).flatMap(f))
  }
}
function Write(value, cont) {
  this.cont = cont
  this.value = value
}
Write.prototype = {
  map(f) {
    return new Write(this.value, this.cont.map(f))
  },
  flatMap(f) {
    return new Write(this.value, this.cont.flatMap(f))
  }
}

function init() {
  return write(1)
}

function nth(n) {
  return read().flatMap(x => (n === 0) ? write(x) : nth(n - 1))
}

// CPS 방식은 추가적인 abstraction이 가능하다.
// function write2(v1, v2, cont) {
//   return write(v1, () => write(v2, cont))
// }

function next() {
  function loop(prev, count) {
    return read().flatMap(c => {
      if (typeof c === 'undefined') return write(count).flatMap(() => write(prev))
      else if (prev === c) return loop(prev, count + 1)
      else return write(count).flatMap(() => write(prev, loop(c, 1)))
    })
  }

  return read().flatMap(prev => loop(prev, 1))
}

let processes = [init()]
for (let i = 0; i < 1000000; i++)
  processes.push(next())
processes.push(nth(1000000))

run(processes, n => console.log(n))


function read() {
  return new Read(x => new Pure(x))
}

function write(value) {
  return new Write(value, new Pure())
}

function run(processes, cb) {
  let stack = []
  while (true) {
    let current = processes.pop()
    if (current instanceof Pure) {        // 지금 프로세스가 종료되었다.
      if (stack.length > 0)               // 아직 읽으려는 process가 있다.
        processes.push(stack.pop()())
      else                                // 마지막 프로세스가 종료되면 끝이다.
        break
    } else if (current instanceof Read) { // 읽으려면 읽기 스택에 넣는다.
      stack.push(current.cont)
    } else {                              // 쓰려면 읽기 스택에서 꺼내 재개한다.
      processes.push(current.cont)
      if (stack.length > 0) {
        processes.push(stack.pop()(current.value))
      } else {                            // 마지막 프로세스에 대해서는 콘솔에 출력한다.
        cb(current.value)
      }
    }
  }
}
