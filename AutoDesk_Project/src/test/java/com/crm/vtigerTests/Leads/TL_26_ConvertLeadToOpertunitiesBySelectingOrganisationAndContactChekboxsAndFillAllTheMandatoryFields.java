package com.crm.vtigerTests.Leads;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.vtiger.genericUtils.BaseClass;
import com.crm.vtiger.pages.ConvertLeadLink;
import com.crm.vtiger.pages.CreateNewLead;
import com.crm.vtiger.pages.HomePage;
import com.crm.vtiger.pages.LeadInformationPage;
import com.crm.vtiger.pages.LeadsPage;
import com.crm.vtiger.pages.OrganizationInfoPage;

@Listeners(com.crm.vtiger.genericUtils.ListImg.class)
public class TL_26_ConvertLeadToOpertunitiesBySelectingOrganisationAndContactChekboxsAndFillAllTheMandatoryFields
		extends BaseClass {

	@Test(groups = "smokeTest")
	public void convertLead() throws InterruptedException, Throwable {

		String expectedLead = eUtil.getExcelData("TC001", 1, 2) + "_" + jutil.getRandomData();
		String companyName = eUtil.getExcelData("TC001", 1, 3) + "_" + jutil.getRandomData();

		// Step 3: click on leads and select a lead
		HomePage homepage = new HomePage(driver, wUtil);
		LeadsPage leads = (LeadsPage) homepage.clickOnMenuLink("leads");

		CreateNewLead newLead = leads.clickOnLeadsMenu();

		LeadInformationPage leadsinfo = newLead.createNewLead(expectedLead, companyName);

		// verification of lead information page
		String actualLeadInfoText = leadsinfo.getLeadText();
		Assert.assertTrue(actualLeadInfoText.contains(expectedLead), "Lead information page is displaying");

		newLead.clickOnLeadLink(expectedLead);

		leads.searchForParticularLead(expectedLead, "Last Name");

		// step4: click on convert lead link
		ConvertLeadLink convertLead = newLead.clickOnConvertLeadLink();

		// verification of convert lead page info
		String actualConvertLeadInfoText = convertLead.getConvertLeadPageInfo();
		Assert.assertTrue(actualConvertLeadInfoText.contains(expectedLead), "convert lead page is displaying");

		convertLead.getSaveButton().click();
		

		// verification for organization name
		OrganizationInfoPage orgInfo = new OrganizationInfoPage(driver, wUtil);
		String actualOrganizationInfo = orgInfo.getOrganizationText();
		Assert.assertTrue(actualOrganizationInfo.contains(companyName), "organization page is displaying");

	}

}
