

function ant(n) {
  let s = ""

  for (let i = 1; i <= n; i++) {
    let next = ""
    let prev = s[0] || ""
    let count = 1
    for (let j = 1; j < s.length; j++) {
      if (prev === s[j]) {
        count++
      } else {
        next += count + prev
        prev = s[j]
        count = 1
      }
    }
    next += count + prev
    s = next
  }

  return s
}

console.log(ant(parseInt(process.argv[2])))

// $ node imperative1.js 65
// Segmentation fault: 11
