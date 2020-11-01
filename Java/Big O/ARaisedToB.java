public class ARaisedToB {

  int power(int a, int b) {
    if (b < 0) {
        return 0; // error
    } else if (b == 0) {
        return 1;
    } else {
        return a* power(a, b - 1);
    }
  }
}

/*
What is the time complexity ?
0(b). The recursive code iterates through b calls, since it subtracts one at each level.
*/