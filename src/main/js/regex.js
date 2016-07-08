
function next(s) {
  return s.replace(/(.)\1*/g, run => run.length + run[0])
}

let s
for (let i = 1; i <= 10; i++) {
  s = s ? next(s) : "1"
  console.log("%d %s", i, s)
}
