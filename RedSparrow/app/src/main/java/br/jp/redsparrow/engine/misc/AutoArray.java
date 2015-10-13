package br.jp.redsparrow.engine.misc;

/**
 * Created by JoaoPaulo on 25/09/2015.
 *
 *  Implementacao simples de um array auto-expansivo
 *
 */
public class AutoArray<T>
{
	private static final int GROWTH = 16;

	private T[] data;
	public int size = 0;

	public AutoArray (int size)
	{
		data = (T[]) new Object[size];
		this.size = 0;
	}

	public AutoArray ()
	{
		this(32);
	}

	private void grow(int newCap)
	{
		T[] olData = data;
		data = (T[]) new Object[newCap];
		System.arraycopy(olData, 0, data, 0, olData.length);
	}

	private void grow()
	{
		int newCap = data.length + GROWTH;
		grow(newCap);
	}

	public T[] getTrimData()
	{
		T[] trimData = (T[]) new Object[size];
		System.arraycopy(data, 0, trimData, 0, size);
		return trimData;
	}

	public void trim()
	{
		data = getTrimData();
	}

	public int add(T o)
	{
		if(size == data.length) grow();

		data[size++] = o;
		return (size - 1);
	}

	public int add(AutoArray<T> a)
	{
		int first = size;
		for (int i = 0; i < a.size; i++)
			add(a.get(i));
		return first;
	}

	public T get(int index)
	{
		return data[index];
	}

	public void set(int index, T o)
	{
		if(index >= data.length)
			grow(index + GROWTH);

		size = index+1;
		data[index] = o;
	}

	public boolean remove(int index)
	{
		if(index >= data.length)
			return false;

		data[index] = data[--size];
		data[size] = null;
		return true;
	}

	public boolean remove(T o)
	{
		for (int i = 0; i < size; i++)
		{
			T o2 = data[i];

			if (o == o2)
			{
				data[i] = data[--size];
				data[size] = null;
				return true;
			}
		}

		return false;
	}

	public boolean remove(AutoArray<T> a)
	{
		boolean modified = false;

		for (int i = 0; i < a.size; i++)
		{
			T o1 = a.get(i);

			for (int j = 0; j < size; j++)
			{
				T o2 = data[j];

				if (o1 == o2)
				{
					remove(j);
//					j--;
					modified = true;
					break;
				}
			}
		}

		return modified;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public void clear()
	{
		if(isEmpty()) return;

		for (int i = 0; i < size; i++) data[i] = null;
		size = 0;
	}
}