package com.cai310.lottery.ticket.dataconver;

import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QyhDataConverCaitong
{
  static int unitsMoney = 2;
  private final List<String> danCorrectList;
  private final List<String> unDanCorrectList;
  private final int combinNum;
  private List<List<String>> resultList;

  public List<List<String>> getResultList()
  {
    return this.resultList;
  }

  public QyhDataConverCaitong(int combinNum, List<String> danCorrectList, List<String> unDanCorrectList)
  {
    this.resultList = new ArrayList();
    this.danCorrectList = danCorrectList;
    this.unDanCorrectList = unDanCorrectList;
    this.combinNum = combinNum;
    init();
  }

  private void init() {
    SplitExtensionCombCallBack call = new SplitExtensionCombCallBack();
    MathUtils.efficientCombExtension(this.combinNum, this.danCorrectList.size(), -1, -1, this.unDanCorrectList.size(), call);
  }
  class SplitExtensionCombCallBack implements ExtensionCombCallBack {
    SplitExtensionCombCallBack() {
    }

    public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
      List combList = new ArrayList();
      for (int i = 0; i < comb1.length; ++i) {
        if (comb1[i]) {
          combList.add((String)QyhDataConverCaitong.this.danCorrectList.get(i));
          if (combList.size() == m1)
            break;
        }
      }
      int n = m1 + m2;
      for (int i = 0; i < comb2.length; ++i) {
        if (comb2[i]) {
          combList.add((String)QyhDataConverCaitong.this.unDanCorrectList.get(i));
          if (combList.size() == n) {
            break;
          }
        }
      }
      
	  Collections.sort(combList);
      QyhDataConverCaitong.this.resultList.add(combList);
      return false;
    }
  }
}