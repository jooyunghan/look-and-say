
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
