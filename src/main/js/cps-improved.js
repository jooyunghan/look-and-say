"use strict"

// improvements over cps.js
// 1. Use Read/Write classes instead of plain JS objects
// 2. Use old `function () {}`s instead of arrow(=>) functions
// 3. Use top-level functions instead of local(nested) functions
// 4. Use values for continuation whereever possible instead of functions
// 20s => 10s

function Read(cont) {
  this.cont = cont
}

function Write(value, cont) {
  this.value = value
  this.cont = cont
}

function read(cont) {
  return new Read(cont)
}

function write(value, cont) {
  return new Write(value, cont)
}

// process stack의 top을 우선 실행
// read 할 때 readers stack에 넣고
// 다음 process 실행.
// write할 때 readers stack에서 꺼내어 resume
function run(processes) {
  let readers = []
  while (true) {
    let current = processes.pop()
    if (typeof current === 'function')
      current = current()

    if (typeof current === 'undefined') { // 지금 프로세스가 종료되었다.
      if (readers.length > 0)               // 아직 읽으려는 process가 있다.
        processes.push(readers.pop())
      else                                  // 마지막 프로세스가 종료되면 끝이다.
        break
    } else if (current instanceof Read) { // 읽으려면 읽기 스택에 넣는다.
      readers.push(current.cont)
    } else {                              // 쓰려면 읽기 스택에서 꺼내 재개한다.
      processes.push(current.cont)
      if (readers.length > 0) {
        let cont = readers.pop()
        processes.push(cont(current.value))
      } else {                            // 마지막 프로세스에 대해서는 콘솔에 출력한다.
        console.log(current.value)
      }
    }
  }
}

// 1 출력하고 끝
function init() {
  return write(1)
}

// 먼저 한글자 읽고 loop
function next() {
  return read(function (c) {
    return loop(c, 1)
  })
}

// 한글자씩 읽으면서 이전글자(prev)와 같으면 갯수(count)를 증가시키고
// 글자가 바뀌거나 리스트가 끝날 때마다 prev/count를 출력 
function loop(prev, count) {
  return read(function (c) {
    if (typeof c === 'undefined')
      return write(count, write(prev))
    else if (prev === c)
      return loop(prev, count + 1)
    else
      return write(count, write(prev, loop(c, 1)))
  })
}

// 한글자씩 읽으며 n을 감소시키다가 n == 0에서 읽은 글자만 출력
function nth(n) {
  return read(function (c) {
    return (n === 0) ? write(c) : nth(n - 1)
  })
}

function ant(n, m) {
  let processes = [init]
  for (let i = 0; i < n; i++)
    processes.push(next)
  processes.push(nth(m))

  run(processes)
}

ant(1000000, 1000000)

