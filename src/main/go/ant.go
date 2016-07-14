package main

import "fmt"

// This code is written by @wonyoung

func next(in, out chan int) {
	prev := <-in
	count := 1
	for v := range in {
		if v == prev {
			count++
		} else {
			out <- count
			out <- prev
			prev = v
			count = 1
		}
	}
	out <- count
	out <- prev
	close(out)
}

func ant(n int) chan int {
	if n == 0 {
		return first()
	}
	o := make(chan int)
	i := ant(n - 1)
	go next(i, o)
	return o
}

func first() chan int {
	ch := make(chan int)
	go func() {
		ch <- 1
		close(ch)
	}()
	return ch
}

func nth(ch chan int, n int) int {
	for i := 1; i<n; i++ {
		<-ch
	}
	return <-ch
}

func main() {
	fmt.Println(nth(ant(100000), 100000))
}
