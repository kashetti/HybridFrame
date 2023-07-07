package driverFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import commonFunctions.FuntionLibrary;
import commonFunctions.FuntionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript extends FuntionLibrary {
	String inputpath ="./FileInput/DataEngine.xlsx";
	String outputpath ="./FileOutput/HybridResults.xlsx";
	String TCSheet ="TestCases";
	String TSSheet ="TestSteps";
	@Test
	public void startTest()throws Throwable
	{
		boolean res = false;
		String tcres="";
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//row count in TCSheet,TSSheet
		int TCCount=xl.rowCount(TCSheet);
		int TSCount = xl.rowCount(TSSheet);
		Reporter.log(TCCount+"       "+TSCount,true);
		//iterate all rows in TCSheet
		for(int i=1;i<=TCCount;i++)
		{
			//read module status cell
			String Module_Status = xl.getCellData(TCSheet, i, 2);
			if(Module_Status.equalsIgnoreCase("Y"))
			{
				//read tcid cell
				String tcid = xl.getCellData(TCSheet, i, 0);
				//iterate all rows in TSSheet
				for(int j=1;j<=TSCount;j++)
				{
					String tsid =xl.getCellData(TSSheet, j, 0);
					if(tcid.equalsIgnoreCase(tsid))
					{
						String keyword =xl.getCellData(TSSheet, j, 3);
						if(keyword.equalsIgnoreCase("adminLogin"))
						{
							String para1 =xl.getCellData(TSSheet, j, 5);
							String para2 =xl.getCellData(TSSheet, j, 6);
							res =FuntionLibrary.PbLogin(para1, para2);
						}
						else if(keyword.equalsIgnoreCase("branchCreation"))
						{
							String para1 =xl.getCellData(TSSheet, j, 5);
							String para2 =xl.getCellData(TSSheet, j, 6);
							String para3 =xl.getCellData(TSSheet, j, 7);
							String para4 =xl.getCellData(TSSheet, j, 8);
							String para5 =xl.getCellData(TSSheet, j, 9);
							String para6 =xl.getCellData(TSSheet, j, 10);
							String para7 =xl.getCellData(TSSheet, j, 11);
							String para8 =xl.getCellData(TSSheet, j, 12);
							String para9 =xl.getCellData(TSSheet, j, 13);
							FuntionLibrary.pBBranches();
							res =FuntionLibrary.pbBranchcreation(para1, para2, para3, para4, para5, para6, para7, para8, para9);
							
						}
						else if(keyword.equalsIgnoreCase("branchUpdate"))
						{
							String para1 =xl.getCellData(TSSheet, j, 5);
							String para2 =xl.getCellData(TSSheet, j, 6);
							String para5 =xl.getCellData(TSSheet, j, 9);
							String para6 =xl.getCellData(TSSheet, j, 10);
							FuntionLibrary.pBBranches();
							res = FuntionLibrary.PbBranchUpdate(para1, para2, para5, para6);
						}
						else if(keyword.equalsIgnoreCase("adminLogout"))
						{
							res =FuntionLibrary.pbLogout();
						}
						String tsres="";
						if(res)
						{
							tsres="Pass";
							xl.setCellData(TSSheet, j, 4, tsres, outputpath);
						}
						else
						{
							tsres="Fail";
							xl.setCellData(TSSheet, j, 4, tsres, outputpath);
						}
						tcres=tsres;
					}
					
				}
				xl.setCellData(TCSheet, i, 3, tcres, outputpath);
			}
			else
			{
				//write as blocked into TCSheet which test case flag to N
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
		}
		
	}
}










