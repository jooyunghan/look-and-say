
function Pure(value) {
  this.value = value
}

Pure.prototype = {
  map(f) {
    return new Pure(f(this.value))
  },
  flatMap(f) {
    return f(this.value)
  },
  toString() {
    return `Pure`
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
  },
  toString() {
    return `Read <>`
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
  },
  toString() {
    return `Write ${this.value} ${this.cont}`
  }
}

function read() {
  return new Read(x => new Pure(x))
}

function write(value) {
  return new Write(value, new Pure())
}

module.exports = {
  read, write, Read, Write, Pure
}