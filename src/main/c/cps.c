#include <assert.h>
#include <stdlib.h>
#include <stdio.h>

typedef struct state {
  char prev;
  char count;
  char next;
  char ptr;
} state;

int init(state *s) {
  switch (s->ptr) {
    case 0:
      s->ptr++;
      return 1;
    default:
      return 0;
  }
}

int next(state *s) {
  int temp;
  switch (s->ptr) {
    case 0:
      s->ptr = 1;
      return -1;
    case 1:
      s->prev = s->next;
      s->count = 1;
      s->ptr = 2;
      return -1;
    case 2:
      if (s->prev == s->next) {
        s->count++;
        return -1;
      } else if (s->next == 0) {
        s->ptr = 3;
        return s->count;
      } else {
        s->ptr = 5;
        return s->count;
      }
    case 3:
      s->ptr = 4;
      return s->prev;
    case 4:
      return 0;
    case 5:
      s->count = 1;
      temp = s->prev;
      s->prev = s->next;
      s->ptr = 6;
      return temp;
    case 6:
      s->ptr = 2;
      return -1;
    default:
      assert("Unreachable");
      return 0;
  }
}

int main() {
  int n = 1000000, m = 1000;
  state* lines = (state*)calloc(n + 1, sizeof(state));
  int cur = n + 1;
  while (1) {
    int result = (cur == 0) ? init(&lines[0]) : next(&lines[cur]);
    switch (result) {
      case -1: // read
        cur--;
        break;
      default: // write 1,2,3
        if (cur < n) {
          cur++;
          lines[cur].next = result;
        } else {
          if (result == 0)
            goto endloop;
          else if (m-->0)
            printf("%d", result);
          else {
            printf("%d", result);
            goto endloop;
          }
        }
        break;
    }
  }
endloop:
  return 0;
}


