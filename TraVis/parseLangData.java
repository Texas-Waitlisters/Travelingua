import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
public class parseLangData 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner sc = new Scanner(new File("languageList.txt"));
		ArrayList<String> validLangs = new ArrayList<String>();		
		while(sc.hasNextLine())
			validLangs.add(sc.nextLine().split("\\s+")[0]);
		sc.close();
		Scanner scan = new Scanner(new File("countriesAndLangsDataRaw.txt"));
		HashMap<String, ArrayList<String>> langs = new HashMap<>();
		while (scan.hasNextLine())
		{
			String s = scan.nextLine();
			String name = s.substring(s.indexOf("\">") + 2, s.indexOf("</a"));
			String list = scan.nextLine();
			list = list.substring(list.indexOf(">") + 1, list.indexOf("</"));
			String[] langList = list.split(", (?![a-z])");
			ArrayList<String> elements = new ArrayList<>();
			System.out.println(Arrays.toString(langList));
			for (int i = 0; i < langList.length; i++)
			{
				String[] arr = langList[i].split(" ");
				if (arr[0].contains(","))
				{
					if (validLangs.contains((arr[0].substring(0, arr[0].length()-1))))
						elements.add(arr[0].substring(0, arr[0].length()-1));
				}
				else{
					for (int j = 0; j < arr.length; j++)
					{
						if (validLangs.contains(arr[j]))
							elements.add(arr[j]);
						else if (arr[j].contains("/"))
							if (validLangs.contains(arr[j].substring(0, arr[j].indexOf("/"))))
								elements.add(arr[j].substring(0, arr[j].indexOf("/")));
					}	
				}
			}			
			System.out.println(elements);
			if (elements.size() > 0)
				langs.put(name, elements);
		}
		scan.close();
	}
}
