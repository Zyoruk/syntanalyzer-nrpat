package tablebuilder;
import Datastructs.SimpleList.SimpleList;

public class follows {
	
	@SuppressWarnings("unchecked")
	public void getFollows(SimpleList<Object[]> matrix){
		SimpleList<Object[]> m_clone = new SimpleList<>(matrix);
		SimpleList<Object[]> followsMatrix = new SimpleList<Object[]>();
		SimpleList<String> follows = new SimpleList<String>();
		
		//Set follows Production
		Object[] followsArr = new Object[2];		
		followsArr[0] = m_clone.getData()[0];
		
		SimpleList<String> implies = (SimpleList<String>) m_clone.getData()[1];
		String imp = implies.getData();
		
		//char first = imp.charAt(0);
		
		while(m_clone.getData()[0] != null){
			StringBuilder sb = new StringBuilder(implies.getData());
			if(sb.indexOf((String) m_clone.getData()[0]) == -1){ //If not found
				((SimpleList<Object[]>) m_clone.getData()[0]).delete();
			}else{
				if((sb.indexOf((String) m_clone.getData()[0])+1) > sb.length()){ //Next is null
					//Se asigna $
				}else{
					sb.charAt(sb.indexOf((String) m_clone.getData()[0]) + 1); //Which elem is.
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
