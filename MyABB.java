import java.util.NoSuchElementException;

public class MyABB<E extends Comparable<E>>{
	private MyNodoABB<E> raiz; //Nodo padre de todos
	private int size; //Cuantos valores tiene el arbol
	
	public MyABB() {
		super(); //El super inicia los atributos con null o 0
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size==0;
	}
	
	public E buscar(E valor) {
		MyNodoABB<E> current = this.raiz;
		while(current!=null) {
			if(valor.equals(current.dato)) {
				return current.dato;
			}else if(valor.compareTo(current.dato)<0) {
				current = current.izq;
			}else {
				current = current.der;
			}
		}
		throw new NoSuchElementException("No se puede regresar un elemento que no esta en el arbol");
	}
	
	public void insertar(E valor) {
		MyNodoABB<E> nuevoNodo = new MyNodoABB<E>(valor);
		nuevoNodo.dato = valor;
		if(this.raiz==null) {
			this.raiz = nuevoNodo;
		}else {
			MyNodoABB<E> current = this.raiz;
			MyNodoABB<E> parent = this.raiz;
			while(current!=null) {
				parent = current;
				if(valor.compareTo(current.dato)<0) {
					current = current.izq;
					if(current==null) {
						parent.izq = nuevoNodo;
						return;
					}
				}else {
					current = current.der;
					if(current==null) {
						parent.der = nuevoNodo;
						return;
					}
				}
			}
		}
	}
	
	public boolean borrar(E valor) {
		MyNodoABB<E> current = this.raiz;
		MyNodoABB<E> parent = this.raiz;
		boolean isLeftChild = true;
		while(current.dato!=valor) {
			parent = current;
			if(valor.compareTo(current.dato)<0) { //go left?
				isLeftChild = true;
				current = current.izq;
			}else { //go right?
				isLeftChild = false;
				current = current.der;
			}
			if(current == null){ //final del arbol, no lo encontro
				return false;
			}
		}
		if(current.izq==null && current.der==null) {
			if(current==this.raiz) { //if root, tree is empty
				this.raiz = null;
			}else if(isLeftChild) { //disconnect from parent
				parent.izq = null;
			}else {
				parent.der = null;
			}
		}
		else if(current.der==null) {//un hijo a la izq
			if(current==this.raiz) {
				this.raiz = current.izq;
			}else if(isLeftChild) {
				parent.izq = current.izq;
			}else {
				parent.der = current.izq;
			}
		}
		else if(current.izq==null) {//un hijo a la der
			if(current==this.raiz) {
				this.raiz = current.der;
			}else if(isLeftChild) {
				parent.izq = current.der;
			}else {
				parent.der = current.der;
			}
		}else {//dos hijos
			MyNodoABB<E> predecesor = getPredecesor(current);
			if(current==this.raiz) {
				this.raiz = predecesor;
			}else if(isLeftChild) {
				parent.izq = predecesor;
			}else {
				parent.der = predecesor;
			}
			predecesor.izq = current.izq;
		}
		return true;
	}
	
	private MyNodoABB<E> getPredecesor(MyNodoABB<E> nodo){
		MyNodoABB<E> parent = nodo;
		MyNodoABB<E> predecesor = nodo;
		MyNodoABB<E> current = nodo.izq;
		while(current!=null) {
			parent = predecesor;
			predecesor = current;
			current = current.der;
		}
		if(predecesor!=nodo.izq) {
			parent.der =predecesor.izq;
			predecesor.izq = nodo.izq;
		}
		return predecesor;
	}
	
	public void preOrden() {
		preOrden(this.raiz);
		System.out.println();
	}
	
	private void preOrden(MyNodoABB<E> current) {
		if(current!=null) {
			System.out.print(current.dato+",");
			preOrden(current.izq);
			preOrden(current.der);
		}
	}
	
	public void inOrden() {
		inOrden(this.raiz);
		System.out.println();
	}
	
	private void inOrden(MyNodoABB<E> current) {
		if(current!=null) {
			inOrden(current.izq);
			System.out.print(current.dato+",");
			inOrden(current.der);
		}
	}
	
	public void posOrden() {
		posOrden(this.raiz);
		System.out.println();
	}
	
	private void posOrden(MyNodoABB<E> current) {
		if(current!=null) {
			posOrden(current.izq);
			posOrden(current.der);
			System.out.print(current.dato+",");
		}
	}
	
	public void izq_der() {
		MyQueue<MyNodoABB<E>> fila = new MyQueue<>();
		MyNodoABB<E> current = this.raiz;
		fila.enqueue(current.izq);
		while(!fila.isEmpty()) {
			current = fila.dequeue();
			System.out.print(current.dato+" ");
			if(current.izq!=null) {
				fila.enqueue(current.izq);
			}
			if(current.der!=null) {
				fila.enqueue(current.der);
			}
		}
	}
	
	public void insertarRecursivo(E valor) {
		this.raiz = insertarRecursivo(this.raiz,valor); 
		this.size++;
	}
	
	private MyNodoABB<E> insertarRecursivo(MyNodoABB<E> current, E valor){
		if(current == null) {
			current = new MyNodoABB<E>(valor);
			return current;
		}
		
		if(valor.compareTo(current.dato)<0) {
			current.izq = insertarRecursivo(current.izq,valor);
		}else if(valor.compareTo(current.dato)>0) {
			current.der = insertarRecursivo(current.izq,valor);
		}
		return current;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyABB<Integer> arbol = new MyABB<>();
		arbol.insertar(100);
		arbol.insertar(50);
		arbol.insertar(200);
		arbol.insertar(20);
		arbol.insertar(70);
		arbol.insertar(150);
		arbol.insertar(300);
		arbol.izq_der();
	}

}
//--------------------------------------------------//
class MyNodoABB<E>{
	E dato;
	MyNodoABB<E> izq,
				 der;
	
	public MyNodoABB(E valor) {
		this(valor,null,null);
	}
	
	public MyNodoABB(E dato, MyNodoABB<E> izq, MyNodoABB<E> der) {
		this.dato = dato;
		this.izq = izq;
		this.der = der;
	}
	
	public E getValor() {
		return dato;
	}
	
	public void setValor(E valor) {
		this.dato = valor;
	}
	
	public MyNodoABB<E> getIzq() {
		return izq;
	}
	
	public void setIzq(MyNodoABB<E> izq) {
		this.izq = izq;
	}
	
	public MyNodoABB<E> getDer() {
		return der;
	}
	
	public void setDer(MyNodoABB<E> der) {
		this.der = der;
	}
	public String toString() {
		return this.dato.toString();
	}
}
