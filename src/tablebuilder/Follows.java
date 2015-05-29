package tablebuilder;
import Datastructs.SimpleList.SimpleList;

public class Follows {
	
	@SuppressWarnings("unchecked")
	public void getFollows(SimpleList<Object[]> matrix){
		SimpleList<Object[]> matrixC = new SimpleList<>(matrix);
		SimpleList<Object[]> maTMP = new SimpleList<>(matrix);
		SimpleList<Object[]> followsMatrix = new SimpleList<Object[]>();
		SimpleList<Character> follows = new SimpleList<Character>();
		String[] produc = new String[matrix.length()];
		
		//Make list of Productions
		int i = 0;
		while(maTMP.getData() != null){
			produc[i] = (String) maTMP.getData()[1];
			maTMP.delete();
			i++;
		}
		
		//find follow of each Production
		for(int o = 0; o < produc.length; o++){
			
			//find follows by implies of one production
			while(matrixC.getData() != null){
				
				SimpleList<String> implies = (SimpleList<String>) matrixC.getData()[1];
				String imp = implies.getData();
				
				//find follows by production
				while(((SimpleList<Object[]>) matrixC.getData()[1]).getData() != null){
					StringBuilder sb = new StringBuilder(imp);
					
					if(sb.indexOf(produc[o]) == -1){ //If production not found
						((SimpleList<Object[]>) matrixC.getData()[1]).delete(); //deletes a node
					
					}else{
						if((sb.indexOf((String) matrixC.getData()[1])+1) > sb.length()){ //Next is null
							//Do not repeat follows
							if(!follows.exists('$')){
								follows.append('$');
							}
						}else{
							//If found, gets follower 
							follows.append(sb.charAt(sb.indexOf((String) matrixC.getData()[0]) + 1)); 
						}
					}
				}
				matrixC.delete();
			}
			//Sets new follows matrix.
			Object[] arr = new Object[2];
			arr[0] = produc[o];
			follows = replaceFirstOf(follows);
			arr[1] = follows;
			follows.clear();
			followsMatrix.append(arr);
		}
	}
	
	private SimpleList<Character> replaceFirstOf(SimpleList<Character> follows){
		//Doing follows list
				SimpleList<Character> tmp = new SimpleList<Character>();
				while(follows.getData() != null){
					
					char first = follows.getData();			
					if(!Character.isUpperCase(first)){
						tmp.append(first);
					}else{
						tmp.append(getFirstOf(Character.toString(first)));
					}			
				}
				follows = tmp; //Replace Upper with firstOf.
				return follows;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
