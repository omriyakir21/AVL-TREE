/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 *
 */

//Omri Yakir
	/*
	username: omriyakir
	id: 318867199
	 */
//Maya Raytan
	/*
	username: mayaraytan
	id: 209085711
	 */


public class AVLTree {
	private static final IAVLNode EXTERNAL_LEAF = AVLNode.EXTERNAL_LEAF;
	private IAVLNode root;
    private IAVLNode min;
    private IAVLNode max;

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
  	if (root == null) {return true;} return false;}

  
  /**
   * private void setEmpty()
   *
   * set tree to empty tree
   */
  private void setEmpty() {
	  this.root = null;
  }
  
  protected IAVLNode[] in_order_arr(IAVLNode n){
  	if (empty()){ return new IAVLNode[0];}
  	else {
		IAVLNode[] arr = new IAVLNode[n.getSize()]; //desired in-order array
		IAVLNode[] arr_stack = new IAVLNode[n.getSize()]; //simulating stack
		IAVLNode x = n;
		int i = 0;
		int cnt =0; // number of nodes in arr_stack
		while(cnt!=0 || x.isRealNode()){
			if (x.isRealNode()) {
				arr_stack[cnt]=x;
				cnt++;
				x = x.getLeft();
			} else {
				x = arr_stack[cnt-1];
				cnt--;
				arr[i] = x;
				i += 1;
				x = x.getRight();
			}
		}
		return arr;
	}
 }
  
  	/**
	 * private String node_search(IAVLNode x,int k)
	 *
	 * Returns the node with key k if it exists in the subtree of x
	 * otherwise, returns null.
	 */
  private IAVLNode node_search(IAVLNode x,int k) {
	  //same as we did in class
	  while (x != null && x.isRealNode()) {
		  if (k == x.getKey()) { return x; }
		  else { if (k < x.getKey()){x=x.getLeft(); }
		  		else{ x=x.getRight(); }}}
	  return null;
  }


 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k) {
	  if (root == null) {
		  return null;
	  }
	  IAVLNode n = node_search(root, k);
	  if (n != null) {
		  return n.getValue();
	  }
	return null;
  }
  /**
	 *private void update_size()
	 *
	 *changes node's size according to it's children 
	 *
	 */
  private void update_size(IAVLNode x) {
	  if (x.isLeaf()) {x.setSize(1);} // node is leaf
	  else if (!x.isRealNode()) {x.setSize(0);} // node is external leaf
	  else x.setSize(x.getLeft().getSize() + x.getRight().getSize() + 1); //node is neither leaf nor External
  }
  
	/**
	 * private void update_sizes_till_root(IAVLNode x)
	 *call update_size(IAVLNode x) to calculate sizes from x to root
	 */
	private void update_sizes_till_root(IAVLNode x){
		while (x!=null){ //going up to root
			update_size(x);
			x=x.getParent();}
	}
  
	/**
	 * public IAVLNode tree_position(IAVLNode x, int k)
	 *
 	 *Look for k in the subtree of x Return the last node encountered
	 *Return null if x==null
	 */
  private IAVLNode tree_position(IAVLNode x, int k) {
	IAVLNode y =null;
  	while (x != null) {
  		  y = x;
		  if (k == x.getKey()) { return x; }
		  else { if (k < x.getKey()){
		  			if(!x.getLeft().isRealNode()){ x=null;}
		  			else{	x = x.getLeft();}}
			     else{ if(!x.getRight().isRealNode()){ x=null; }
			           else { x = x.getRight();}}}}
	  return y;
  }
  
	/**
	 * private boolean tree_insert(IAVLNode x ,IAVLNode z)
	 * insert IAVLNODE z to the AVL tree.
	 *
	 * Return true if valid insert(z.getkey() doesnt exists) else false
	 */
  private boolean tree_insert(IAVLNode x ,IAVLNode z) {
  	IAVLNode y = tree_position(x ,z.getKey());
  	if (z.getKey() == y.getKey()){ return false;  }// insertion is not valid.
  	z.setParent(y);
  	if (z.getKey()< y.getKey()){ y.setLeft(z); return true;} // z is the left child of y.
  	y.setRight(z);return true; // z is the right child of y.
  }
	/**
	 * private String node_type(IAVLNode x)
	 *
	 * rd = rank difference
	 * Returns the NodeType of the given node:
	 */
	private static String rank_type(IAVLNode x) {
		if (x==null||!x.isRealNode()){ return null;}
		String rd_left = String.valueOf(x.getHeight() - x.getLeft().getHeight());
		String rd_right = String.valueOf(x.getHeight() - x.getRight().getHeight());
		return(rd_left+rd_right);
	}

	/**
	 * make a right rotation
	 *
	 */
	protected void rotate_right(IAVLNode y){
		IAVLNode x = y.getLeft();
		IAVLNode a = x.getLeft();
		IAVLNode b = x.getRight();
		IAVLNode c = y.getRight();
		x.setParent(y.getParent());
		if (root == y){root =x;}
		else{
			if (x.getParent().getRight() == y){ x.getParent().setRight(x);} else {x.getParent().setLeft(x);}}
		x.setRight(y);
		y.setParent(x);
		y.setLeft(b);
		b.setParent(y);
		y.setSize(b.getSize()+c.getSize()+1);
		x.setSize(y.getSize()+a.getSize()+1);
	}

	/**
	 * make a left rotation
	 *
	 */
	protected void rotate_left(IAVLNode x){
		IAVLNode y = x.getRight();
		IAVLNode a = x.getLeft();
		IAVLNode b = y.getLeft();
		IAVLNode c = y.getRight();
		y.setParent(x.getParent());
		if (root == x){root =y;}
		else{
			if (y.getParent().getRight() == x){ y.getParent().setRight(y);} else {y.getParent().setLeft(y);}}
		y.setLeft(x);
		x.setParent(y);
		x.setRight(b);
		b.setParent(x);
		x.setSize(a.getSize()+b.getSize()+1);
		y.setSize(x.getSize()+c.getSize()+1);
	}
	/**
	 * make a double right rotation
	 *
	 */

	protected void double_rotate_right(IAVLNode z){
		IAVLNode x = z.getLeft();
		IAVLNode y = z.getRight();
		IAVLNode a = x.getLeft();
		IAVLNode b = x.getRight();
		IAVLNode c = b.getLeft();
		IAVLNode d = b.getRight();
		rotate_left(x);
		rotate_right(z);
	}
	/**
	 * make a double left rotation
	 *
	 */

	protected void double_rotate_left(IAVLNode z) {
		IAVLNode x = z.getRight();
		IAVLNode y = z.getLeft();
		IAVLNode b = x.getLeft();
		IAVLNode a = x.getRight();
		IAVLNode d = b.getLeft();
		IAVLNode c = b.getRight();
		rotate_right(x);
		rotate_left(z);
	}
	/**
	 * private int case_01_in(IAVLNode p)
	 * rebalancing node 01 type after insertion
	 * Returns number of reballancing steps.
	 */
	private int case_01_in(IAVLNode p){
		p.promote();
		return 1;}

	/**
	 * private int case_02p(IAVLNode p)
	 * rebalancing node 02p type after insertion
	 * Returns number of reballancing steps.
	 */
	private int case_02p(IAVLNode p) {
		IAVLNode x = p.getLeft();
		switch (rank_type(x)) {
			case ("12"):
				rotate_right(p);
				p.demote();
				return 2;
			case ("21"):
				double_rotate_right(p);
				p.demote();
				x.demote();
				x.getParent().promote();
				return 5;
			case ("11"): // case join
				rotate_right(p);
				x.promote();
				return 2;
		}
		return 0;//will never get here
	}

	/**
	 * private int case_20p(IAVLNode p)
	 * rebalancing node 20p type after insertion
	 * Returns number of reballancing steps.
	 */

	private int case_20p(IAVLNode p){
		IAVLNode x = p.getRight();
		switch (rank_type(x)){
			case("21"):
				rotate_left(p);
				p.demote();
				return 2;//check if i need to return 2 or 3
			case("12"):
				double_rotate_left(p);
				p.demote();
				x.demote();
				x.getParent().promote();
				return 5;
			case ("11")://case join
				rotate_left(p);
				x.promote();
				return 2;
				}
		return 0;//will never get here

	}

	private int rebalance(IAVLNode p ){
		int rebalanceSteps = 0;
		IAVLNode t = p;
		String type = rank_type(p);
		while(t!=null&&((type!=null)&&(!(type.equals("11") || type.equals("12")|| type.equals("21"))))){
			String typeLeftSon = rank_type(t.getLeft());
			String typeRightSon = rank_type(t.getRight());
			switch (type){
				//cases insert
				case("01"):
				case("10"):
					rebalanceSteps+=case_01_in(t);
					t= t.getParent();
					type = rank_type(t);
					break;
				case("20"):
					rebalanceSteps+=case_20p(t);
					t=t.getParent().getParent();
					type = rank_type(t);
					break;
				case ("02"):
					rebalanceSteps+=case_02p(t);
					t=t.getParent().getParent();
					type = rank_type(t);
					break;
				//cases delete
				case("22"):
					rebalanceSteps += case_22_del(t);
					t= t.getParent();
					type = rank_type(t);
					break;
				case ("31"):
					if (t.getRight().isRealNode() && typeRightSon.equals("11")) {
						rebalanceSteps+=case_31_11_del(t);
						t=null;
						break;}
					if (t.getRight().isRealNode() && typeRightSon.equals("21")) {
						rebalanceSteps+=case_31_21_del(t); //rotate left
						t= t.getParent().getParent();
						type = rank_type(t);
						break;}
					if (t.getRight().isRealNode() && typeRightSon.equals("12")) {
						rebalanceSteps+=case_31_12_del(t);
						t= t.getParent().getParent();
						type = rank_type(t);
						break;}
				case ("13"):
					if (t.getLeft().isRealNode() && typeLeftSon.equals("11")) {
						rebalanceSteps+=case_13_11_del(t);
						t=null;
						break;}
					if (t.getLeft().isRealNode() && typeLeftSon.equals("21")) {
						rebalanceSteps+=case_13_21_del(t); //rotate right
						t= t.getParent().getParent();
						type = rank_type(t);
						break;}
					if (t.getLeft().isRealNode() && typeLeftSon.equals("12")) {
						rebalanceSteps+=case_13_12_del(t);
						t= t.getParent().getParent();
						type = rank_type(t);
						break;}}}
		return rebalanceSteps;
	}



  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
   	 IAVLNode my_node = new AVLNode(k,i);
   	 int rebalanceSteps = 0;//counting reballance steps
   	 if (empty()){
   	 	root = my_node;
   	 	root.setSize(1);
        max = my_node;
        min = my_node;}
   	 else{
   	 	if (!tree_insert(root,my_node)){return -1; }//tree_insert making the neccesery insert
		//and returns true iff the insertion was valid ,otherwise return false.
   	 	else{
            if (my_node.getKey() > max.getKey()){max = my_node;}
            if (my_node.getKey() < min.getKey()){min = my_node;}
			update_sizes_till_root(my_node); // change_sizes updating the sizes of my_node and all his parents
			rebalanceSteps = rebalance(my_node.getParent());// reballance reballancing the tree and
			// return number of raballancing steps.
   	 	}}
   	 return rebalanceSteps;
   }

    /**
     * private IAVLNode find_predeccessor(IAVLNode x)
     * find IAVLNODE z predeccessor of x
     *
     * Return predeccessor of x if x is not min otherwise x
     */
    private IAVLNode find_predeccessor(IAVLNode x) {
        //same as we did in class
        if (x.getLeft().isRealNode()){
            return max_node(x.getLeft());
        }
        IAVLNode y = x.getParent();
        while(y != null && x == y.getLeft()){
            x = y;
            y = x.getParent();
        }
        return y;
    }


   
   /**
	 * private IAVLNode find_successor(IAVLNode x)
	 * find IAVLNODE z successor of x
	 *
	 * Return successor of x if x is not max otherwise x
	 */
   private IAVLNode find_successor(IAVLNode x) {
	   //same as we did in class
	 if (x.getRight().isRealNode()){
		 return min_node(x.getRight());
	 }
	 IAVLNode y = x.getParent();
	 while(y != null && x == y.getRight()){
		 x = y;
		 y = x.getParent();
	 }
	 return y;
   }
   
	/**
	 * private int case_22_del(IAVLNode p)
	 * rebalancing node 31 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_22_del(IAVLNode p){
		p.demote();
		return 1;}
	
	/**
	 * private int case_31_11_del(IAVLNode p)
	 * rebalancing node 31 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_31_11_del(IAVLNode p){
		rotate_left(p);
		p.demote();
		p.getParent().promote();
		return 3;}
	
	/**
	 * private int case_31_21_del(IAVLNode p)
	 * rebalancing node 31_21 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_31_21_del(IAVLNode p){
		rotate_left(p);
		p.demote();p.demote();
		return 2;}

	
	/**
	 * private int case_13_12_del(IAVLNode p)
	 * rebalancing node 13_12 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_31_12_del(IAVLNode p){
		double_rotate_left(p);
		p.demote();p.demote();
		p.getParent().promote();
		p.getParent().getRight().demote();
		return 5;}

	
	/**
	 * private int case_13_11_del(IAVLNode p)
	 * rebalancing node 13_11 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_13_11_del(IAVLNode p){
		rotate_right(p);
		p.demote();
		p.getParent().promote();
		return 3;}


	/**
	 * private int case_13_21_del(IAVLNode p)
	 * rebalancing node 13_21 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_13_21_del(IAVLNode p){
		double_rotate_right(p);
		p.demote();p.demote();
		p.getParent().promote();
		p.getParent().getLeft().demote();
		return 5;}



	/**
	 * private int case_13_12_del(IAVLNode p)
	 * rebalancing node 13_12 type after deletion
	 * Returns number of reballancing steps.
	 */
	private int case_13_12_del(IAVLNode p){
		rotate_right(p);
		p.demote();p.demote();
		return 2;}
   
   
   /**
    * public int delete(int k)
    *
    * Deletes an item with key k from the binary tree, if it is there.
    * The tree must remain valid, i.e. keep its invariants.
    * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
    * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
    * Returns -1 if an item with key k was not found in the tree.
    */
       public int delete(int k)
       {
           IAVLNode inTree = node_search(root, k);
           if (inTree == null) {return -1;} // key not in tree

           IAVLNode pre = find_predeccessor(inTree);
           IAVLNode suc = find_successor(inTree);

           int rebalanceSteps = delete_rec(k);

           if (inTree == max){max = pre;}
           if (inTree == min){min = suc;}

           return rebalanceSteps;
       }


    /**
     * public int delete_rec(int k)
     *
     */
   private int delete_rec(int k)
   {
       IAVLNode inTree = node_search(root, k);
       if (inTree == null) {return -1;} // key not in tree
       boolean deleteRoot = false; //do we delete root?
       int rebalanceSteps = 0; //calculate rotates
       if (root == inTree) {deleteRoot = true;}
       if (deleteRoot && inTree.getSize() == 1) { //after deletion tree will be empty
           setEmpty();
           return 0;}
       if (inTree.isLeaf()) { //node to delete is a leaf
           change_node(inTree, EXTERNAL_LEAF);}
       //node to delete has one child
       else if (!inTree.getLeft().isRealNode()) {change_node(inTree, inTree.getRight());}
       else if (!inTree.getRight().isRealNode()) {change_node(inTree, inTree.getLeft());}
       else { //node to delete has two children
           IAVLNode successor = find_successor(inTree);
           rebalanceSteps += delete_rec(successor.getKey()); //recursive call
           successor.setHeight(1 + Math.max(inTree.getLeft().getHeight(), inTree.getRight().getHeight())); //set successor height
           change_node(inTree, successor);} // change the deleted node the it's successor
       if (deleteRoot) {rebalanceSteps += rebalance(this.root);} //rebalance from new root if we deleted root
       else {rebalanceSteps += rebalance(inTree.getParent());} //rebalance from the parent of the node we deleted
       return rebalanceSteps;
   }

   /**
    * public String change_node(IAVLNode x, IAVLNode y)
    *
    * change node x to node y
    * 
    */
   private void change_node(IAVLNode x, IAVLNode y)
   {
	   if (this.root == x) {this.root = y;}
	   //connect y to x's parent
	   y.setParent(x.getParent());
	   // connect x parents to y
	   if (x.getParent() != null && x.getParent().getLeft() == x) {
		   x.getParent().setLeft(y);}
	   else if (x.getParent() != null) {x.getParent().setRight(y);}
	   //set x children's to y
	   if (x.getLeft().isRealNode() && x.getLeft() != y){
		   x.getLeft().setParent(y);
		   y.setLeft(x.getLeft());}
	   if (x.getRight().isRealNode() && x.getRight() != y) {
		   x.getRight().setParent(y);
		   y.setRight(x.getRight());}
	   update_sizes_till_root(y); //update sizes
   }
   
   
   /**
    * public String min_node(IAVLNode x)
    *
    * Returns the node with the smallest key in the sub tree of x,
    * or null if the x is null.
    */
   private IAVLNode min_node(IAVLNode x)
   {
	   if (x == null || !x.isRealNode()) {return null;} // x is null
	   while (x.getLeft().isRealNode()) // going down to the left node if it exists
	   {
		   x = x.getLeft();
	   }
	   return x; //return the most left real node
   }
   
   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
       if (min == null){return null;}
       return min.getValue();
   }

   /**
    * public String max_node(IAVLNode x)
    *
    * Returns the node with the largest key in the sub tree of x,
    * or null if the x is null.
    */
   private IAVLNode max_node(IAVLNode x)
   {
	   if (x == null || !x.isRealNode()) {return null;} // x is null
	   while (x.getRight().isRealNode()) // going down to the left node if it exists
	   {
		   x = x.getRight();
	   }
	   return x; //return the most left real node
   }
   
   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
       if (max == null){return null;}
       return max.getValue();
   }


  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
  	IAVLNode [] arr = in_order_arr(root);
  	int [] key_arr = new int [size()];
  	for (int i=0;i<arr.length;i++){
  		key_arr[i]=arr[i].getKey();	}
        return key_arr;
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
  	IAVLNode [] arr = in_order_arr(root);
  	String [] key_arr = new String [size()];
  	for (int i=0;i<arr.length;i++){
  		key_arr[i]=arr[i].getValue();	}
  	return key_arr;

  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
   	   if (root == null){return 0;}
	   return root.getSize(); // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)   {

	   IAVLNode y = node_search(root,x);
       IAVLNode pre = find_predeccessor(y);
       IAVLNode suc = find_successor(y);
	   AVLTree small = new AVLTree();
	   AVLTree big =new AVLTree();
	   AVLTree [] two_trees = {small,big};
	   if (y.getLeft().isRealNode()){
		   small.root = y.getLeft() ;
		   small.root.setParent(null);
		   y.setLeft(EXTERNAL_LEAF);}
	   if (y.getRight().isRealNode()){
		   big.root = y.getRight();
		   big.root.setParent(null);
		   y.setRight(EXTERNAL_LEAF);}
	   IAVLNode p = y.getParent();

	   while (p!=null){

		   AVLTree tmp = new AVLTree();
		   IAVLNode t=null;
		   if (p.getRight()==y){
			   if (p.getLeft().isRealNode()){
				   tmp.root = p.getLeft();
				   tmp.root.setParent(null);}
			   t = p.getParent();
			   small.join(p,tmp);}

		   else {
			   if (p.getRight().isRealNode()){
				   tmp.root = p.getRight();
				   tmp.root.setParent(null);}
			   t = p.getParent();
			   big.join(p,tmp);}
		   y=p;
		   p=t;
	   }
       if (min != max){
           small.min = min;
           small.max = pre;
           big.min = suc;
           big.max = max;
       }
	   return two_trees;
   }


   /**
    * private IAVLNode[] findNodeOnRightByHeight(int k)
	*
	* res[0] = first node on right spine of tree with height <= k
	* if node is EXTERNAL_LEAF, res[1] = node.parent
    */

	private IAVLNode[] findNodeOnRightByHeight(int k){
		IAVLNode node = root;
		IAVLNode[] res = new IAVLNode[2];
		while (node.getHeight() > k) {
			if (!node.getRight().isRealNode()){res[1] = node;} //if node to join is EXTERNAL_LEAF, we keep it's parent
			node = node.getRight(); //go to the right son
		}
		res[0] = node; //node is the first node in tree which it's height <= k
		return res;
	}

	/**
	 * private IAVLNode[] findNodeOnLeftByHeight(int k)
	 *
	 * res[0] = first node on left spine of tree with height <= k
	 * if node is EXTERNAL_LEAF, res[1] = node.parent
	 */

	private IAVLNode[] findNodeOnLeftByHeight(int k){
		IAVLNode node = root;
		IAVLNode[] res = new IAVLNode[2];
		while (node.getHeight() > k) {
			if (!node.getLeft().isRealNode()){res[1] = node;} //if node to join is EXTERNAL_LEAF, we keep it's parent
			node = node.getLeft(); //go to the left son
		}
		res[0] = node; //node is the first node in tree which it's height <= k
		return res;
	}

	/**
	 * private void join_x_to_t1(IAVLNode x, AVLTree t1)
	 *
	 * connect x to t1
	 * precondition: t1 < x
	 */
	private void join_x_to_t1(IAVLNode x, AVLTree t1){
		int k = -1; //height of empty tree
		x.setHeight(k+1);
		IAVLNode[] join_nodes = t1.findNodeOnRightByHeight(k);
		IAVLNode join_node = join_nodes[0];
		x.setParent(join_nodes[1]);
		//else {x.setParent(join_node.getParent());}
		//if (join_node.getParent()!= null) {join_node.getParent().setLeft(x);}
		join_nodes[1].setRight(x);
		x.setLeft(join_node);
		//if (join_node.isRealNode()) {join_node.setParent(x);}
		this.root = t1.getRoot();
		this.update_sizes_till_root(x);
		this.rebalance(x.getParent());
	}
	/**
	 * private void join_x_to_t2(IAVLNode x, AVLTree t2)
	 *
	 * connect x to t2
	 * precondition: x < t2
	 */
	private void join_x_to_t2(IAVLNode x, AVLTree t2){
		int k = -1; //height of empty tree
		x.setHeight(k+1);
		IAVLNode[] join_nodes = t2.findNodeOnLeftByHeight(k);
		IAVLNode join_node = join_nodes[0];
		x.setParent(join_nodes[1]);
		//else {x.setParent(join_node.getParent());}
		//if (join_node.getParent()!= null) {join_node.getParent().setLeft(x);}
		join_nodes[1].setLeft(x);
		x.setRight(join_node);
		//if (join_node.isRealNode()) {join_node.setParent(x);}
		this.root = t2.getRoot();
		this.update_sizes_till_root(x);
		this.rebalance(x.getParent());
	}

	/**
	 * private void join_same_height(AVLTree t1, IAVLNode x, AVLTree t2)
	 *
	 * connect x to t1's root, connect x t2's root
	 * precondition: t1 < x < t2
	 * precondition: rank(t1) == rank(t2)
	 */
	private void join_same_height(AVLTree t1, IAVLNode x, AVLTree t2){
		if (!t1.empty() && !t2.empty()){
			t1.getRoot().setParent(x);
			x.setLeft(t1.getRoot());
			x.setHeight(t1.getRoot().getHeight() + 1);
			x.setRight(t2.getRoot());
			t2.getRoot().setParent(x);}
		this.root = x;
		this.update_sizes_till_root(x);
		this.rebalance(x);
	}
	/**
    * private void join_t2_is_higher(AVLTree t1, IAVLNode x, AVLTree t2)
	*
	* connect x to t1's root, connect x to wanted node in t2
    * precondition: t1 < x < t2
	* precondition: rank(t1) < rank(t2)
    */   
   private void join_t2_is_higher(AVLTree t1, IAVLNode x, AVLTree t2){
	   int k = t1.root.getHeight();
	   t1.getRoot().setParent(x);
	   x.setLeft(t1.getRoot());
	   x.setHeight(k+1);
	   IAVLNode[] join_nodes = t2.findNodeOnLeftByHeight(k);
	   IAVLNode join_node = join_nodes[0];
	   if (!join_node.isRealNode()) {x.setParent(join_nodes[1]);}
	   else {x.setParent(join_node.getParent());}
	   if (!join_node.isRealNode()){join_nodes[1].setLeft(x);}
	   else if (join_node.getParent()!= null) {join_node.getParent().setLeft(x);}
	   x.setRight(join_node);
	   if (join_node.isRealNode()) {join_node.setParent(x);}
	   this.root = t2.getRoot();
	   this.update_sizes_till_root(x);
	   this.rebalance(x.getParent());
   }

	/**
	 * private void join_t1_is_higher(AVLTree t1, IAVLNode x, AVLTree t2)
	 *
	 * connect x to t2's root, connect x to wanted node in t1
	 * precondition: t1 < x < t2
	 * precondition: rank(t2) < rank(t1)
	 */
	private void join_t1_is_higher(AVLTree t1, IAVLNode x, AVLTree t2){
		int k = t2.root.getHeight();
		t2.getRoot().setParent(x);
		x.setRight(t2.getRoot());
		x.setHeight(k+1);
		IAVLNode[] join_nodes = t1.findNodeOnRightByHeight(k);
		IAVLNode join_node = join_nodes[0];
		if (!join_node.isRealNode()) {x.setParent(join_nodes[1]);}
		else {x.setParent(join_node.getParent());}
		if (!join_node.isRealNode()){join_nodes[1].setRight(x);}
		else if (join_node.getParent()!= null) {join_node.getParent().setRight(x);}
		x.setLeft(join_node);
		if (join_node.isRealNode()) {join_node.setParent(x);}
		this.root = t1.getRoot();
		this.update_sizes_till_root(x);
		this.rebalance(x.getParent());
	}

   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   //initialized x//
	   x.setParent(null);
	   x.setRight(EXTERNAL_LEAF);
	   x.setLeft(EXTERNAL_LEAF);
	   update_size(x);
	   x.setHeight(0);

	   int height_diff = 1;
	   //cases//
	   if (this.empty() && t.empty()) { //both trees are empty
		   this.root = x;
		   this.min = x;
		   this.root = x;}

	   else if (t.empty()) { //only t is empty
		   height_diff += this.root.getHeight();
		   if (this.getRoot().getKey() > x.getKey()){
			   join_x_to_t2(x,this);
			   this.min = x;}
		   else {join_x_to_t1(x,this);
			   this.max = x; }}

	   else if (this.empty()){ //only this is empty
		   height_diff += t.root.getHeight();
		   if (t.getRoot().getKey() > x.getKey()){
			   join_x_to_t2(x,t);
			   this.max = t.max;
			   this.min = x;}
		   else {
			   join_x_to_t1(x,t);
			   this.min = t.min;
			   this.max = x;}}

	   else{ //trees are not empty
		   int new_height_diff = this.getRoot().getHeight() - t.getRoot().getHeight();
		   boolean this_is_greater = this.getRoot().getKey() > x.getKey();
		   if (new_height_diff > 0){ //rank(t) < rank(this)
			   height_diff += new_height_diff;
			   if (this_is_greater){join_t2_is_higher(t, x, this);
				   this.min = t.min;}
			   else {join_t1_is_higher(this, x, t);}}
		   else if (new_height_diff < 0) { //rank(this) < rank(t)
			   height_diff += Math.abs(new_height_diff);
			   if (this_is_greater){join_t1_is_higher(t, x, this);}
			   else {join_t2_is_higher(this, x, t);}}
		   else { // rank(t) == rank(this)
			   if (this_is_greater){join_same_height(t, x, this);}
			   else {join_same_height(this, x, t);}}
		   if (this_is_greater){this.min = t.min;}
		   else {this.max = t.max;}}

	   return height_diff;}

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{

		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
		public void setSize(int s);// Sets the size of the node's subtree.
		public int getSize();// Returns the size of the node's subtree(0 for virtual nodes).
		public void promote(); // add 1 to node's height
		public void demote();// decrease 1 to node's height
		public boolean isLeaf(); // check if node is leaf (it's boys are External_leaf)

	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public static class AVLNode implements IAVLNode{
  	private static final AVLNode EXTERNAL_LEAF= new AVLNode();
  	private IAVLNode left;
  	private IAVLNode right;
  	private IAVLNode parent;
  	private int rank;
  	private int key;
  	private String info;
  	private int size;

	public AVLNode(){
		this.key = -1;
		this.info =  null;
		this.rank = -1;
		this.size = 0;
	}

  	public AVLNode(int key, String info){
  		this.key = key;
  		this.info = info;
  		size = 1;
  		left = EXTERNAL_LEAF;
  		right = EXTERNAL_LEAF;
 	}


  	public int getKey()	{ return key;}
  	public String getValue() {	return info;}
  	public void setLeft(IAVLNode node)	{left = node;}
  	public IAVLNode getLeft() {	return left; }
  	public void setRight(IAVLNode node)	{ right = node;	}
  	public IAVLNode getRight(){	return right;}
  	public void setParent(IAVLNode node){ parent = node; }
  	public IAVLNode getParent()	{ return parent; }
  	public boolean isRealNode()	{ if (this == EXTERNAL_LEAF){ return false;}return true;}
  	public void setHeight(int height) { rank = height; }
  	public int getHeight() {return rank;}
  	public void setSize(int s) {  size = s;}
  	public int getSize(){return size;}
  	public void promote() { rank+=1;   }
  	public void demote() { rank-=1;   }
    public boolean isLeaf(){if (!this.isRealNode()) {return false;}
	else if (!this.getLeft().isRealNode() && !this.getRight().isRealNode()) {return true;}
		return false;}

   }}
