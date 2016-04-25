package edu.monash.jmare.cvproj_gcs;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by james on 25/04/16.
 * This class not great, need to use JSON for all this so it can be pretty
 */
public class ParamLocal implements Serializable {
    public int greyThreshMin;
    public int greyThreshMax;
    public int greyErodePix;
    public int greyDilatePix;
    public int greyErodeIterations;
    public int greyDilateIterations;
    public int candMaxNumObjects;
    public int candMinObjectArea;
    public int candMaxObjectArea;
    public int checkSquareSize;
    public int checkHMin;
    public int checkHMax;
    public int checkSMin;
    public int checkSMax;
    public int checkMinGreen;
    public int calcScorePercentColor;
    public int calcScorePercentColorExtra;
    public int calcScorePercentSize;
    public int calcScorePercentCircle;
    public int calcAreaExpected;
    public int calcAreaMaxDiff;
    public int calcMinPairDist;
    public int calcMaxPairDist;
    public int masterMinScoreNonPair;
    public int masterMinScorePair;
    public int masterPairScoreBoost;
    public int gimGainX;
    public int gimGainY;
    public int gimMovementInt;

    public void parseString(String paramRaw) {
        //This function takes a raw string set of parameters and parses them
        //Error checking should be done before the string gets to this function

        int start = 3;
        greyThreshMin = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        greyThreshMax =Integer.parseInt(paramRaw.substring(start, start +3));
        start = start+3;
        greyErodePix =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        greyDilatePix =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        greyErodeIterations =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        greyDilateIterations =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        candMaxNumObjects = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        candMinObjectArea =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        candMaxObjectArea =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        checkSquareSize = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        checkHMin = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        checkHMax = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        checkSMin = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        checkSMax = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        checkMinGreen = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcScorePercentColor = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcScorePercentColorExtra =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcScorePercentSize = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcScorePercentCircle =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcAreaExpected = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcAreaMaxDiff = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcMinPairDist = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        calcMaxPairDist = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        masterMinScoreNonPair = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        masterMinScorePair = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        masterPairScoreBoost =Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        gimGainX = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        gimGainY = Integer.parseInt(paramRaw.substring(start, start+3));
        start = start+3;
        gimMovementInt = Integer.parseInt(paramRaw.substring(start, start+3));
    }

    public String packString(){
        String packedString = "SMX" +
                //this makes each integer into a 3 digit string
             String.format(Locale.getDefault(), "%03d", greyThreshMin) +
             String.format(Locale.getDefault(), "%03d", greyThreshMax) +
             String.format(Locale.getDefault(), "%03d", greyErodePix) +
             String.format(Locale.getDefault(), "%03d", greyDilatePix) +
             String.format(Locale.getDefault(), "%03d", greyErodeIterations) +
             String.format(Locale.getDefault(), "%03d", greyDilateIterations) +
             String.format(Locale.getDefault(), "%03d", candMaxNumObjects) +
             String.format(Locale.getDefault(), "%03d", candMinObjectArea) +
             String.format(Locale.getDefault(), "%03d", candMaxObjectArea) +
             String.format(Locale.getDefault(), "%03d", checkSquareSize )+
             String.format(Locale.getDefault(), "%03d", checkHMin )+
             String.format(Locale.getDefault(), "%03d", checkHMax ) +
             String.format(Locale.getDefault(), "%03d", checkSMin ) +
             String.format(Locale.getDefault(), "%03d", checkSMax )+
             String.format(Locale.getDefault(), "%03d", checkMinGreen) +
             String.format(Locale.getDefault(), "%03d", calcScorePercentColor) +
             String.format(Locale.getDefault(), "%03d", calcScorePercentColorExtra) +
             String.format(Locale.getDefault(), "%03d", calcScorePercentSize )+
             String.format(Locale.getDefault(), "%03d", calcScorePercentCircle) +
             String.format(Locale.getDefault(), "%03d", calcAreaExpected )+
             String.format(Locale.getDefault(), "%03d", calcAreaMaxDiff )+
             String.format(Locale.getDefault(), "%03d", calcMinPairDist )+
             String.format(Locale.getDefault(), "%03d", calcMaxPairDist )+
             String.format(Locale.getDefault(), "%03d", masterMinScoreNonPair) +
             String.format(Locale.getDefault(), "%03d", masterMinScorePair )+
             String.format(Locale.getDefault(), "%03d", masterPairScoreBoost) +
             String.format(Locale.getDefault(), "%03d", gimGainX )+
             String.format(Locale.getDefault(), "%03d", gimGainY )+
             String.format(Locale.getDefault(), "%03d", gimMovementInt) +
                "EMX";
        return packedString;
    }
}