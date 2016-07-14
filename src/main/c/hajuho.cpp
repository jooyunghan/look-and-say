// Written by Ha Juho

#include <cassert>
#include <iostream>
using namespace std;

typedef enum {
    // invalid
    LT_X = 0,
    // tokens
    LT11, LT12, LT13, LT21, LT22, LT23, LT31, LT32, LT33,
    LT11_, LT12_, LT13_, LT21_, LT22_, LT23_, LT31_, LT32_, LT33_,
    LT_LAST
} LookToken;
typedef enum {
    // undone 0~2
    LS = 0,
    LS1, LS2, LS3,
    LS11, LS12, LS13, LS21, LS22, LS23, LS31, LS32, LS33,
    // done 0~2
    LS_,
    LS1_, LS2_, LS3_,
    LS11_, LS12_, LS13_, LS21_, LS22_, LS23_, LS31_, LS32_, LS33_,
    // undone 3~4
    LS111, LS112, LS113, LS121, LS122, LS123, LS131, LS132, LS133,
    LS211, LS212, LS213, LS221, LS222, LS223, LS231, LS232, LS233,
    LS311, LS312, LS313, LS321, LS322, LS323, LS331, LS332, LS333,
    LS1111, LS1112, LS1113, LS1121, LS1122, LS1123, LS1131, LS1132, LS1133,
    LS1211, LS1212, LS1213, LS1221, LS1222, LS1223, LS1231, LS1232, LS1233,
    LS1311, LS1312, LS1313, LS1321, LS1322, LS1323, LS1331, LS1332, LS1333,
    LS2111, LS2112, LS2113, LS2121, LS2122, LS2123, LS2131, LS2132, LS2133,
    LS2211, LS2212, LS2213, LS2221, LS2222, LS2223, LS2231, LS2232, LS2233,
    LS2311, LS2312, LS2313, LS2321, LS2322, LS2323, LS2331, LS2332, LS2333,
    LS3111, LS3112, LS3113, LS3121, LS3122, LS3123, LS3131, LS3132, LS3133,
    LS3211, LS3212, LS3213, LS3221, LS3222, LS3223, LS3231, LS3232, LS3233,
    LS3311, LS3312, LS3313, LS3321, LS3322, LS3323, LS3331, LS3332, LS3333,
    // done 3~4
    LS111_, LS112_, LS113_, LS121_, LS122_, LS123_, LS131_, LS132_, LS133_,
    LS211_, LS212_, LS213_, LS221_, LS222_, LS223_, LS231_, LS232_, LS233_,
    LS311_, LS312_, LS313_, LS321_, LS322_, LS323_, LS331_, LS332_, LS333_,
    LS1111_, LS1112_, LS1113_, LS1121_, LS1122_, LS1123_, LS1131_, LS1132_, LS1133_,
    LS1211_, LS1212_, LS1213_, LS1221_, LS1222_, LS1223_, LS1231_, LS1232_, LS1233_,
    LS1311_, LS1312_, LS1313_, LS1321_, LS1322_, LS1323_, LS1331_, LS1332_, LS1333_,
    LS2111_, LS2112_, LS2113_, LS2121_, LS2122_, LS2123_, LS2131_, LS2132_, LS2133_,
    LS2211_, LS2212_, LS2213_, LS2221_, LS2222_, LS2223_, LS2231_, LS2232_, LS2233_,
    LS2311_, LS2312_, LS2313_, LS2321_, LS2322_, LS2323_, LS2331_, LS2332_, LS2333_,
    LS3111_, LS3112_, LS3113_, LS3121_, LS3122_, LS3123_, LS3131_, LS3132_, LS3133_,
    LS3211_, LS3212_, LS3213_, LS3221_, LS3222_, LS3223_, LS3231_, LS3232_, LS3233_,
    LS3311_, LS3312_, LS3313_, LS3321_, LS3322_, LS3323_, LS3331_, LS3332_, LS3333_,
    LS_LAST
} LineState;
const LookToken TOKENS[LS_LAST] = {
    // undone 0~2
    LT_X,
    LT_X, LT_X, LT_X,
    LT_X, LT11, LT11, LT21, LT_X, LT21, LT31, LT31, LT_X,
    // done 0~2
    LT_X,
    LT11_, LT21_, LT31_,
    LT12_, LT11, LT11, LT21, LT22_, LT21, LT31, LT31, LT32_,
    // undone 3~4
    LT13, LT12, LT12, LT11, LT11, LT11, LT11, LT11, LT11,
    LT21, LT21, LT21, LT22, LT23, LT22, LT21, LT21, LT21,
    LT31, LT31, LT31, LT31, LT31, LT31, LT32, LT32, LT33,
    LT_X, LT13, LT13, LT12, LT12, LT12, LT12, LT12, LT12,
    LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11,
    LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11,
    LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21,
    LT22, LT22, LT22, LT23, LT_X, LT23, LT22, LT22, LT22,
    LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21,
    LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31,
    LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31,
    LT32, LT32, LT32, LT32, LT32, LT32, LT33, LT33, LT_X,
    // done 3~4
    LT13_, LT12, LT12, LT11, LT11, LT11, LT11, LT11, LT11,
    LT21, LT21, LT21, LT22, LT23_, LT22, LT21, LT21, LT21,
    LT31, LT31, LT31, LT31, LT31, LT31, LT32, LT32, LT33_,
    LT_X, LT13, LT13, LT12, LT12, LT12, LT12, LT12, LT12,
    LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11,
    LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11, LT11,
    LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21,
    LT22, LT22, LT22, LT23, LT_X, LT23, LT22, LT22, LT22,
    LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21, LT21,
    LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31,
    LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31, LT31,
    LT32, LT32, LT32, LT32, LT32, LT32, LT33, LT33, LT_X
};
const LineState LOOKED[LS_LAST] = {
    // undone 0~2
    LS,
    LS1, LS2, LS3,
    LS11, LS2, LS3, LS1, LS22, LS3, LS1, LS2, LS33,
    // done 0~2
    LS_,
    LS_, LS_, LS_,
    LS_, LS2_, LS3_, LS1_, LS_, LS3_, LS1_, LS2_, LS_,
    // undone 3~4
    LS, LS2, LS3, LS21, LS22, LS23, LS31, LS32, LS33,
    LS11, LS12, LS13, LS1, LS, LS3, LS31, LS32, LS33,
    LS11, LS12, LS13, LS21, LS22, LS23, LS1, LS2, LS,
    LS1111, LS2, LS3, LS21, LS22, LS23, LS31, LS32, LS33,
    LS211, LS212, LS213, LS221, LS222, LS223, LS231, LS232, LS233,
    LS311, LS312, LS313, LS321, LS322, LS323, LS331, LS332, LS333,
    LS111, LS112, LS113, LS121, LS122, LS123, LS131, LS132, LS133,
    LS11, LS12, LS13, LS1, LS2222, LS3, LS31, LS32, LS33,
    LS311, LS312, LS313, LS321, LS322, LS323, LS331, LS332, LS333,
    LS111, LS112, LS113, LS121, LS122, LS123, LS131, LS132, LS133,
    LS211, LS212, LS213, LS221, LS222, LS223, LS231, LS232, LS233,
    LS11, LS12, LS13, LS21, LS22, LS23, LS1, LS2, LS3333,
    // done 3~4
    LS_, LS2_, LS3_, LS21_, LS22_, LS23_, LS31_, LS32_, LS33_,
    LS11_, LS12_, LS13_, LS1_, LS_, LS3_, LS31_, LS32_, LS33_,
    LS11_, LS12_, LS13_, LS21_, LS22_, LS23_, LS1_, LS2_, LS_,
    LS1111_, LS2_, LS3_, LS21_, LS22_, LS23_, LS31_, LS32_, LS33_,
    LS211_, LS212_, LS213_, LS221_, LS222_, LS223_, LS231_, LS232_, LS233_,
    LS311_, LS312_, LS313_, LS321_, LS322_, LS323_, LS331_, LS332_, LS333_,
    LS111_, LS112_, LS113_, LS121_, LS122_, LS123_, LS131_, LS132_, LS133_,
    LS11_, LS12_, LS13_, LS1_, LS2222_, LS3_, LS31_, LS32_, LS33_,
    LS311_, LS312_, LS313_, LS321_, LS322_, LS323_, LS331_, LS332_, LS333_,
    LS111_, LS112_, LS113_, LS121_, LS122_, LS123_, LS131_, LS132_, LS133_,
    LS211_, LS212_, LS213_, LS221_, LS222_, LS223_, LS231_, LS232_, LS233_,
    LS11_, LS12_, LS13_, LS21_, LS22_, LS23_, LS1_, LS2_, LS3333_
};
const LineState SAID[LT_LAST][LS33 + 1] = {
    { // LT_X
        LS_LAST,
        LS_LAST, LS_LAST, LS_LAST,
        LS_LAST, LS_LAST, LS_LAST, LS_LAST, LS_LAST, LS_LAST, LS_LAST, LS_LAST, LS_LAST
    },
    { // LT11
        LS11,
        LS111, LS211, LS311,
        LS1111, LS1211, LS1311, LS2111, LS2211, LS2311, LS3111, LS3211, LS3311
    },
    { // LT12
        LS12,
        LS112, LS212, LS312,
        LS1112, LS1212, LS1312, LS2112, LS2212, LS2312, LS3112, LS3212, LS3312
    },
    { // LT13
        LS13,
        LS113, LS213, LS313,
        LS1113, LS1213, LS1313, LS2113, LS2213, LS2313, LS3113, LS3213, LS3313
    },
    { // LT21
        LS21,
        LS121, LS221, LS321,
        LS1121, LS1221, LS1321, LS2121, LS2221, LS2321, LS3121, LS3221, LS3321
    },
    { // LT22
        LS22,
        LS122, LS222, LS322,
        LS1122, LS1222, LS1322, LS2122, LS2222, LS2322, LS3122, LS3222, LS3322
    },
    { // LT23
        LS23,
        LS123, LS223, LS323,
        LS1123, LS1223, LS1323, LS2123, LS2223, LS2323, LS3123, LS3223, LS3323
    },
    { // LT31
        LS31,
        LS131, LS231, LS331,
        LS1131, LS1231, LS1331, LS2131, LS2231, LS2331, LS3131, LS3231, LS3331
    },
    { // LT32
        LS32,
        LS132, LS232, LS332,
        LS1132, LS1232, LS1332, LS2132, LS2232, LS2332, LS3132, LS3232, LS3332
    },
    { // LT33
        LS33,
        LS133, LS233, LS333,
        LS1133, LS1233, LS1333, LS2133, LS2233, LS2333, LS3133, LS3233, LS3333
    },
    { // LT11_
        LS11_,
        LS111_, LS211_, LS311_,
        LS1111_, LS1211_, LS1311_, LS2111_, LS2211_, LS2311_, LS3111_, LS3211_, LS3311_
    },
    { // LT12_
        LS12_,
        LS112_, LS212_, LS312_,
        LS1112_, LS1212_, LS1312_, LS2112_, LS2212_, LS2312_, LS3112_, LS3212_, LS3312_
    },
    { // LT13_
        LS13_,
        LS113_, LS213_, LS313_,
        LS1113_, LS1213_, LS1313_, LS2113_, LS2213_, LS2313_, LS3113_, LS3213_, LS3313_
    },
    { // LT21_
        LS21_,
        LS121_, LS221_, LS321_,
        LS1121_, LS1221_, LS1321_, LS2121_, LS2221_, LS2321_, LS3121_, LS3221_, LS3321_
    },
    { // LT22_
        LS22_,
        LS122_, LS222_, LS322_,
        LS1122_, LS1222_, LS1322_, LS2122_, LS2222_, LS2322_, LS3122_, LS3222_, LS3322_
    },
    { // LT23_
        LS23_,
        LS123_, LS223_, LS323_,
        LS1123_, LS1223_, LS1323_, LS2123_, LS2223_, LS2323_, LS3123_, LS3223_, LS3323_
    },
    { // LT31_
        LS31_,
        LS131_, LS231_, LS331_,
        LS1131_, LS1231_, LS1331_, LS2131_, LS2231_, LS2331_, LS3131_, LS3231_, LS3331_
    },
    { // LT32_
        LS32_,
        LS132_, LS232_, LS332_,
        LS1132_, LS1232_, LS1332_, LS2132_, LS2232_, LS2332_, LS3132_, LS3232_, LS3332_
    },
    { // LT33_
        LS33_,
        LS133_, LS233_, LS333_,
        LS1133_, LS1233_, LS1333_, LS2133_, LS2233_, LS2333_, LS3133_, LS3233_, LS3333_
    }
};
const int FIRST_DIGIT[LS33_ + 1] = {
    // undone 0~2
    9,
    9, 9, 9,
    1, 1, 1, 2, 2, 2, 3, 3, 3,
    // done 0~2
    9,
    9, 9, 9,
    1, 1, 1, 2, 2, 2, 3, 3, 3
};
const int SECOND_DIGIT[LS33_ + 1] = {
    // undone 0~2
    9,
    9, 9, 9,
    1, 2, 3, 1, 2, 3, 1, 2, 3,
    // done 0~2
    9,
    9, 9, 9,
    1, 2, 3, 1, 2, 3, 1, 2, 3
};
const bool IS_DONE_STATE[LS_LAST] = {
    // undone 0~2
    false,
    false, false, false,
    false, false, false, false, false, false, false, false, false,
    // done 0~2
    true,
    true, true, true,
    true, true, true, true, true, true, true, true, true,
    // undone 3~4
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    false, false, false, false, false, false, false, false, false,
    // done 3~4
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true,
    true, true, true, true, true, true, true, true, true
};
inline bool tryLookAndSayAGroup(LineState* lines, int li)
{
    assert(!IS_DONE_STATE[lines[li]]);
    LineState upper = lines[li - 1];
    LookToken token = TOKENS[upper];
    if (!token) {
        return false;
    }
    lines[li] = SAID[token][lines[li]];
    lines[li - 1] = LOOKED[upper];
    return true;
}
int lookAndSayState(int n, long long m)
{
    if (n < 0 || m < 0) {
        return 0;
    } else if (n == 0) {
        if (m == 0) {
            return 1;
        } else {
            return 0;
        }
    }
    // initialize
    LineState* lines = new LineState[n + 1];
    lines[0] = LS1_;
    for (int i = 1; i <= n; i++) {
        lines[i] = LS;
    }
     
    // lazy evaluation
    int res = 0;
    int li = 2; // evaluating line number
    while (true) {
        do {
            assert(2 <= li && li <= n + 1);
            do {
                li--;
                assert(1 <= li && li <= n);
            } while (!tryLookAndSayAGroup(lines, li));
            assert(1 <= li && li <= n);
            do {
                li++;
                assert(2 <= li && li <= n + 1);
            } while (li <= n && tryLookAndSayAGroup(lines, li));
        } while (li <= n);
        assert(li == n + 1);
        assert((LS11 <= lines[n] && lines[n] <= LS33) || (LS11_ <= lines[n] && lines[n] <= LS33_));
        m -= 2;
        if (m < 0) {
            if (m == -2) {
                res = FIRST_DIGIT[lines[n]];
            } else {
                res = SECOND_DIGIT[lines[n]];
            }
            break;
        } else if (IS_DONE_STATE[lines[n]]) {
            break;
        }
        lines[n] = LS;
    }
    delete[] lines;
    return res;
}
unsigned long long lookAndSayState(int n)
{
    if (n < 0) {
        return 0;
    } else if (n == 0) {
        return 1;
    }
    // initialize
    LineState* lines = new LineState[n + 1];
    lines[0] = LS1_;
    for (int i = 1; i <= n; i++) {
        lines[i] = LS;
    }
    // lazy evaluation
    unsigned long long cnt = 0;
    int li = 2;
    do {
        lines[n] = LS;
        do {
            assert(2 <= li && li <= n + 1);
            do {
                li--;
                assert(1 <= li && li <= n);
            } while (!tryLookAndSayAGroup(lines, li));
            assert(1 <= li && li <= n);
            do {
                li++;
                assert(2 <= li && li <= n + 1);
            } while (li <= n && tryLookAndSayAGroup(lines, li));
        } while (li <= n);
        assert(li == n + 1);
        assert((LS11 <= lines[n] && lines[n] <= LS33) || (LS11_ <= lines[n] && lines[n] <= LS33_));
        cnt += 2;
    } while (!IS_DONE_STATE[lines[n]]);
    delete[] lines;
    return cnt;
}

int main() {
  cout << lookAndSayState(1000000, 1000000) << endl;
  return 0;
}