function* next() {
  let prev = yield null
  let count = 1
  while (true) {
    let c = yield null
    if (c == 0) {
      break
    } else if (prev === c) {
      count++
    } else {
      yield count
      yield prev
      prev = c
      count = 1
    }
  }
  yield count
  yield prev
}

function nth(n) {
  return function* () {
    while (n-- > 0) {
      yield null
    }
    yield (yield null)
  } ()
}

function run(processes) {
  let n = processes.length - 1;
  let value
  while (true) {
    let next = processes[n].next(value)
    if (next.done) {
      if (n == processes.length - 1) {
        process.stdout.write('\n')
        return
      } else {
        n++
        value = 0
      }
    } else {
      if (next.value === null) { // read
        n--
      } else if (n == processes.length - 1) { // write
        process.stdout.write(String(next.value))
      } else {
        n++
        value = next.value
      }
    }
  }
}

let processes = []
processes.push([1].entries())
for (let i = 0; i < 1000000; i++)
  processes.push(next())
processes.push(nth(1000000))

run(processes);

// generator로 coroutine을 구현하였다.