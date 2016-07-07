
function runLength(arr) {
  if (arr.length == 0) return []

  let result = []
  let prev = arr[0]
  let count = 1
  for (let i = 1; i < arr.length; i++) {
    if (prev === arr[i]) {
      count++
    } else {
      result.push([prev, count])
      prev = arr[i]
      count = 1
    }
  }
  result.push([prev, count])
  return result
}

function flatten(arr) {
  return [].concat(...arr)
}

let s
for (let i = 1; i <= 10; i++) {
  s = s ? flatten(runLength(s)) : "1"
  console.log("%d %s", i, s.join(""))
}

// $ node runlength.js
// 1 1
// 2 11
// 3 12
// 4 1121
// 5 122111
// 6 112213
// 7 12221131
// 8 1123123111
// 9 12213111213113
// 10 11221131132111311231