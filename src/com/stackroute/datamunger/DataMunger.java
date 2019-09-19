package com.stackroute.datamunger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.ArrayList;

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {

		String str1=queryString.toLowerCase();
		String[] words =str1.split(" ");

		return words;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {

		String[] words =queryString.split(" ");
		int len=words.length;

		int i;
		for(i=0;i<len;i++)
		{
			if(words[i].equals("from"))
				break;
		}

		return words[i+1];
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery( String queryString) {

		String [] words=queryString.split("\\s");
		int len=words.length;


		String str="";

		if(!words[0].equals("where"))
			str+=words[0];
		else
			return str;

		for(int i=1;i<len;i++)
		{
			if(!words[i].equals("where"))
			{
				str+=" "+words[i];
			}
			else
				break;
		}

		return str;
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
		String[] str11= queryString.split(" ");
		int i=0;
		String allFields;
		String temp=str11[0];
		while(true) {
			if (temp.equals("select")) {
				allFields = str11[i + 1];
				break;
			}
			else{
				i++;
				temp=str11[i];
			}

		}
		String[] fields=allFields.split(",");
		return fields;

	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
        
		int input1 = queryString.indexOf("where") +6;
		if(queryString.matches("order by"))
		{
			int input2 = queryString.indexOf("order by");
			input2=input2-1;
			queryString= queryString.substring(input1,input2);
			return queryString.toLowerCase();
		}
		if(queryString.matches("order by"))
		{
			int input2 = queryString.indexOf("group by");
			input2=input2-1;
			queryString= queryString.substring(input1,input2);
			return queryString.toLowerCase();
		}
		return queryString.substring(input1).toLowerCase();


	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {


		queryString=queryString.toLowerCase();
		String result1[]=null;
		if(queryString.contains("where")) {
			int input1 = queryString.indexOf("where");
			input1 = input1 + 6;            if (queryString.contains("order by")) {
				int input2 = queryString.indexOf("order by");
				input2 = input2 - 1;
				queryString = queryString.substring(input1, input2);                String[] result2 = queryString.split(" and | or ");
				return result2;
			}
			if (queryString.contains("group by")) {
				int input2 = queryString.indexOf("group by");
				input2 = input2 - 1;
				queryString = queryString.substring(input1, input2);                String[] result3 = queryString.split(" and | or ");
				return result3;
			}            queryString = queryString.substring(input1);            String[] result4 = queryString.split(" and | or ");
			return result4;
		}
		return null;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {

		String[] myNew= queryString.split(" ");
		int l=myNew.length;
		int k=0;
		if(queryString.contains("where")) {
			for (int i = 0; i < l; i++) {
				if (myNew[i].equals("and") || myNew[i].equals("or") || myNew[i].equals("not"))
					k++;
			}
			int j = 0;
			String[] result1 = new String[k];
			for (int i = 0; i < l; i++) {
				if ((myNew[i].equals("and") || myNew[i].equals("or") || myNew[i].equals("not")) && (k != 0)) {
					result1[j] = myNew[i];
					j++;
					k--;
				}
			}
			return result1;
		}
		return null;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {
		String[] result2 = null;
		if (queryString.contains("order by")) {
			int index = queryString.indexOf("order by")+9;
			String newString = queryString.substring(index, queryString.length());
			 String[] result1 = newString.split(",");
			return result1;
		}
		return result2;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {
		if(queryString.contains("group by")) {
			int index = queryString.indexOf("group") + 9;
			String myNew = queryString.substring(index, queryString.length());
			String[] myNewArr = myNew.split(",");
			return myNewArr;
		}
		return null;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public static String[] getAggregateFunctions(String queryString) {
		if(queryString.contains("(")) {
            String[] temp1 = queryString.split(" ");
            int l = temp1.length;
            ArrayList<String> myList = new ArrayList<String>();
            ArrayList<String> myList1 = new ArrayList<String>();

            StringBuilder str = new StringBuilder();
            for (int i = 0; i < l; i++) {
                if (temp1[i].contains("(")) {
                    str.append(temp1[i]).append(" ");
                }
            }
            String[] arr = str.toString().replaceAll(",", " ").replaceAll(" +", " ").split(" ");
            StringBuffer sb = new StringBuffer();

            for (int k = 0; k < arr.length; k++) {
                if (arr[k].contains("(")) {
                    sb.append(arr[k]).append(" ");
//                    System.out.println(arr[k]);
                }
            }
            String[] finalAggregate = sb.toString().split(" ");

            return finalAggregate;
        }
		return null;
	}

}