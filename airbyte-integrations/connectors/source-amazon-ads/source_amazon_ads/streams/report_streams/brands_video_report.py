#
# MIT License
#
# Copyright (c) 2020 Airbyte
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#

from .report_streams import ReportStream

METRICS_MAP = {
    "keywords": [
        "campaignName",
        "campaignId",
        "campaignStatus",
        "campaignBudget",
        "campaignBudgetType",
        "campaignRuleBasedBudget",
        "applicableBudgetRuleId",
        "applicableBudgetRuleName",
        "adGroupName",
        "adGroupId",
        "keywordText",
        "keywordBid",
        "keywordStatus",
        "targetId",
        "targetingExpression",
        "targetingText",
        "targetingType",
        "matchType",
        "impressions",
        "clicks",
        "cost",
        "attributedSales14d",
        "attributedSales14dSameSKU",
        "attributedConversions14d",
        "attributedConversions14dSameSKU",
    ],
    "adGroups": [
        "campaignName",
        "campaignId",
        "campaignStatus",
        "campaignBudget",
        "campaignBudgetType",
        "adGroupName",
        "adGroupId",
        "impressions",
        "clicks",
        "cost",
        "attributedSales14d",
        "attributedSales14dSameSKU",
        "attributedConversions14d",
        "attributedConversions14dSameSKU",
    ],
    "campaigns": [
        "campaignName",
        "campaignId",
        "campaignStatus",
        "campaignBudget",
        "campaignBudgetType",
        "campaignRuleBasedBudget",
        "applicableBudgetRuleId",
        "applicableBudgetRuleName",
        "impressions",
        "clicks",
        "cost",
        "attributedSales14d",
        "attributedSales14dSameSKU",
        "attributedConversions14d",
        "attributedConversions14dSameSKU",
    ],
}


class SponsoredBrandsVideoReportStream(ReportStream):
    """
    https://advertising.amazon.com/API/docs/en-us/reference/sponsored-brands/2/reports
    """

    def report_init_endpoint(self, record_type: str) -> str:
        return f"/v2/hsa/{record_type}/report"

    metrics_map = METRICS_MAP

    def _get_init_report_body(self, report_date: str, record_type: str, profile):
        metrics_list = self.metrics_map[record_type]
        body = {
            "reportDate": report_date,
            "creativeType": "video",
        }
        return {**body, "metrics": ",".join(metrics_list)}
