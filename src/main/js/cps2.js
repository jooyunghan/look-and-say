
function init() {
  return write(1)
}

function nth(n) {
  return read(x => (n === 0) ? write(x) : nth(n - 1))
}

// CPS 방식은 추가적인 abstraction이 가능하다.
// function write2(v1, v2, cont) {
//   return write(v1, () => write(v2, cont))
// }

function next() {
  function loop(prev, count) {
    return read(c => {
      if (typeof c === 'undefined') return write(count, write(prev))
      else if (prev === c) return loop(prev, count + 1)
      else return write(count, write(prev, loop(c, 1)))
    })
  }

  return read((prev) => loop(prev, 1))
}

let processes = [init]
for (let i = 0; i < 1000000; i++)
  processes.push(next)
processes.push(() => nth(1000000))

run(processes, n => console.log(n))

function read(cont) {
  return { type: 'read', cont }
}

function write(value, cont) {
  return { type: 'write', cont, value }
}

function run(processes, cb) {
  let stack = []
  while (true) {
    let current = processes.pop()
    if (typeof current === 'function') current = current()
    if (typeof current === 'undefined') { // 지금 프로세스가 종료되었다.
      if (stack.length > 0)               // 아직 읽으려는 process가 있다.
        processes.push(stack.pop().cont)
      else                                // 마지막 프로세스가 종료되면 끝이다.
        break
    } else if (current.type === 'read') { // 읽으려면 읽기 스택에 넣는다.
      stack.push(current)
    } else {                              // 쓰려면 읽기 스택에서 꺼내 재개한다.
      processes.push(current.cont)
      if (stack.length > 0) {
        let next = stack.pop().cont
        let value = current.value
        processes.push(next(value))
      } else {                            // 마지막 프로세스에 대해서는 콘솔에 출력한다.
        cb(current.value)
      }
    }
  }
}
