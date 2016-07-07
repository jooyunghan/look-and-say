
let s = ""

for (let i = 1; i <= 10; i++) {
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

  console.log("%d %s", i, s)
}

// $ node imperative.js
// 1 1
// 2 11
// 3 21
// 4 1211
// 5 111221
// 6 312211
// 7 13112221
// 8 1113213211
// 9 31131211131221
// 10 13211311123113112211