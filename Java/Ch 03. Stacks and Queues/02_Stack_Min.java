/*
Q:
Stack Min: How would you design a stack which, in addition to push and pop, has a function min
which returns the minimum element? Push, pop and min should all operate in 0(1) time.

A:
The thing with minimums is that they don't change very often. They only change when a smaller element
is added.
One solution is to have just a single int value, minValue, that's a member of the Stack class. When
minValue is popped from the stack, we search through the stack to find the new minimum. Unfortunately,
this would break the constraint that push and pop operate in 0( 1) time.
To further understand this question, let's walk through it with a short example:
push(5); // stack is {5}, min is 5
push(6); // stack is {6, 5}, min is 5
push(3); // stack is {3, 6, 5}, min is 3
push(7); // stack is {7, 3, 6, 5}, min is 3
pop(); // pops 7. stack is {3, 6, 5}, min is 3
pop(); // pops 3. stack is {6, 5}. min is 5.
Observe how once the stack goes back to a prior state ( { 6, 5} ), the minimum also goes back to its prior
state (5). This leads us to our second solution.
If we kept track of the minimum at each state, we would be able to easily know the minimum. We can do
this by having each node record what the minimum beneath itself is. Then, to find the min, you just look at
what the top element thinks is the min.
When you push an element onto the stack, the element is given the current minimum. It sets its "local
min"to be the min.

There's just one issue with this: if we have a large stack, we waste a lot of space by keeping track of the min
for every single element. Can we do better?
*/

package Q3_02_Stack_Min;

import java.util.Stack;

public class StackWithMin extends Stack<NodeWithMin> {
    
    //Remember, push method decides what is min, not min() method, min() just gives current min
    public void push(int value) {
        int newMin = Math.min(value, min());
        super.push(new NodeWithMin(value, newMin));
    }
    
    public int min() {
    	if (this.isEmpty()) {			// Stack is empty
    		return Integer.MAX_VALUE;
    	} else {
    		return peek().min;
    	}
    }
}

class NodeWithMin {
    public int value;
    public int min;
    public NodeWithMin(int v, int min){
        value = v;
        this.min = min;
    }
}

/*
A:
There's just one issue with this: if we have a large stack, we waste a lot of space by keeping track of the min
for every single element. Can we do better?
We can (maybe) do a bit better than this by using an additional stack which keeps track of the mins.
Why might this be more space efficient? Suppose we had a very large stack and the first element inserted
happened to be the minimum. In the first solution, we would be keeping n integers, where n is the size of
the stack. In the second solution though, we store just a few pieces of data: a second stack with one element
and the members within this stack.
*/

package Q3_02_Stack_Min;
import java.util.Stack;

public class StackWithMin2 extends Stack<Integer> {
	Stack<Integer> s2;
	
	public StackWithMin2() {
		s2 = new Stack<Integer>();		
	}
	
	public void push(int value){
		// "=" in "<=" is important as we push min number of times we see it so its in sync w/ pop
		//Remember, push method decides what is min, not min() method, min() just gives current min
		if (value <= min()) { 
			s2.push(value);
		}
		super.push(value);
	}
	
	public Integer pop() {
		int value = super.pop();
		//IMP : Pop min from other stack if it matches with value to be removed from main stack
		if (value == min()) {
			s2.pop();			
		}
		return value;
	}
	
	public int min() {
		if (s2.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return s2.peek();
		}
	}
}
