## 1

## 2

```cs
public class Bag {
    private int[] elems;
    private int count;

    public Bag(int[] initialElements) {
        Contract.Requires(initialElements != null);
        this.count = initialElements.Length;
        int[] e = new int[initialElements.Length];
        initialElements.CopyTo(e, 0);
        this.elems = e;
    }

    public Bag(int[] initialElements, int start, int howMany) {
        Contract.Requires(
            initialElements != null && 
            start >= 0 && 
            howMany >= 0 &&
            start + howMany < initialElements.Length
        );
        this.count = howMany;
        int[] e = new int[howMany];
        Array.Copy(initialElements, start, e, 0, howMany);
        this.elems = e;
    }

    [Pure]
    public int Count() {
        return count;
    }

    [Pure]
    public int[] GetElements() {
        return elems;
    }

    public int RemoveMin() {
        Condition.Requires(count > 0);
        int m = System.Int32.MaxValue;
        int mindex = 0;
        for(int i = 0; i < count; i++) {
            if(elems[i] < m) {
                mindex = i;
                m = elems[i];
            }
        }
        count--;
        elems[mindex] = elems[count];
        return m;
    }

    public void Add(int x) {
        Condition.Requires();
        if(count == elems.Length) {
            int[] b =new int[2*elems.Length];
            Array.Copy(elems, 0, b, 0, elems.Length);
            elems = b;
        }
        elems[count] = x;
        count++;
    }

    [ContractInvariantMethod]
    private void ObjectInvariant() {
        
    }
}
```