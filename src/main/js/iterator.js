

function *next(line) {
  let prev = line.next().value
  let count = 1
  for (let c of line) {
    if (prev === c)
      count++
    else {
      yield count
      yield prev
      prev = c
      count = 1
    }
  }
  yield count
  yield prev
}

function ant(n) {
  // initial line
  let line = [1][Symbol.iterator]()

  // wrap lines n times
  for (let i=1;i<n; i++) {
    line = next(line)
  }

  return line
}

function print(gen) {
  for (let e of gen)
    process.stdout.write(String(e))
}

function length(gen) {
  let count = 0
  for (let e of gen)
    count++
  return count
}

function nth(n,gen) {
  while (n --> 0)
    gen.next()
  return gen.next().value
}

//print(ant(100))
console.log(nth(1000000, ant(5941))) // maximum value without StackOverflow
