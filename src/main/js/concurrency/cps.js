class C {
  constructor(f) {
    this.f = f    // f:: (a -> Action m) -> Action m 
  }

  static pure(x) {
    return new C(c => c(x))
  }

  map(f) {
    return new C(c => this.f(a => c(f(a))))
  }

  flatMap(f) {
    return new C(c => this.f(a => f(a).f(c)))
  }
}

// Action data constructors

class Atom {
  constructor(m) {
    this.m = m
  }
}

class Fork {
  constructor(a1, a2) {
    this.a1 = a1
    this.a2 = a2
  }
}

class Stop {
}

// action :: C m a -> Action m
function action(cma) {
  return cma.f(a => new Stop())
}

// atom :: m a -> C m a
function atom(m) {
  return new C(c => new Atom(m.map(a => c(a))))
}

// stop :: C m a
function stop() {
  return new C(c => new Stop())
}

function par(m1, m2) {
  return new C(c => new Fork(m1.f(c), m2.f(c)))
}

function fork(m) {
  return new C(c => new Fork(action(m), c()))
}

// round :: [Action m] -> m ()
function round(actions) {
  while (actions.length > 0) {
    let a = actions.
    if (a instanceof Atom) {
      a.m.flatMap(aa => round(actions.concat(aa)))
      break
    } else if (a instanceof Fork) {
      actions.push(a.a1, a.a2)
    } else {
      continue;
    }
  }
}