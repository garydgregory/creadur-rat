package org.apache.rat.report.claim.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.rat.api.Document;
import org.apache.rat.api.RatException;
import org.apache.rat.api.Reporter;
import org.apache.rat.api.MetaData;
import org.apache.rat.report.claim.ClaimStatistic;


/**
 * The aggregator is used to create a numerical statistic
 * of claims.
 */
public class ClaimAggregator extends AbstractClaimReporter {
    private final Reporter reporter;
    private final Map numsByLicenseFamilyName = new HashMap();
    private final Map numsByLicenseFamilyCode = new HashMap();
    private final Map numsByFileType = new HashMap();
    private int numApproved, numUnApproved, numGenerated, numUnknown;

    public ClaimAggregator(Reporter pReporter) {
        reporter = pReporter;
    }

    private void incMapValue(Map pMap, Object pKey) {
        final Integer num = (Integer) pMap.get(pKey);
        final int newNum;
        if (num == null) {
            newNum = 1;
        } else {
            newNum = num.intValue() + 1;
        }
        pMap.put(pKey, new Integer(newNum));
    }
    
    protected void handleDocumentCategoryClaim(String documentCategoryName) {
        incMapValue(numsByFileType, documentCategoryName);
    }

    protected void handleApprovedLicenseClaim(String licenseApproved) {
        if (MetaData.RAT_APPROVED_LICENSE_VALUE_TRUE.equals(licenseApproved)) {
            numApproved++;
        } else {
            numUnApproved++;
        }
    }

    protected void handleLicenseFamilyNameClaim(String licenseFamilyName) {
        incMapValue(numsByLicenseFamilyName, licenseFamilyName);
    }

    protected void handleHeaderCategoryClaim(String headerCategory) {
        
        if (MetaData.RAT_LICENSE_FAMILY_CATEGORY_VALUE_GEN.equals(headerCategory)) {
            numGenerated++;
            incMapValue(numsByLicenseFamilyCode, MetaData.RAT_LICENSE_FAMILY_CATEGORY_VALUE_GEN);
        } else if (MetaData.RAT_LICENSE_FAMILY_CATEGORY_VALUE_UNKNOWN.equals(headerCategory)) {
            numUnknown++;
            incMapValue(numsByLicenseFamilyCode, MetaData.RAT_LICENSE_FAMILY_CATEGORY_VALUE_UNKNOWN);
        }
    }

    public void fillClaimStatistic(ClaimStatistic pStatistic) {
        pStatistic.setDocumentCategoryMap(numsByFileType);
        pStatistic.setLicenseFileCodeMap(numsByLicenseFamilyCode);
        pStatistic.setLicenseFileNameMap(numsByLicenseFamilyName);
        pStatistic.setNumApproved(numApproved);
        pStatistic.setNumGenerated(numGenerated);
        pStatistic.setNumUnApproved(numUnApproved);
        pStatistic.setNumUnknown(numUnknown);
    }


    public void report(Document document) throws RatException {
        super.report(document);
        if (reporter != null) {
            reporter.report(document);
        }
    }
}
