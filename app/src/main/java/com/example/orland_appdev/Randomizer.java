package com.example.orland_appdev;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Randomizer extends BaseActivity {

    EditText txtName, txtAmount, txtBet1, txtBet2, txtBet3, txtBet4, txtBet5, txtBet6, txtRandom1, txtRandom2, txtRandom3, txtRandom4, txtRandom5, txtRandom6;
    Button btnPlaceBet, btnShowBets, btnShowResults, btnStart;
    TextView lblResult;

    InputFilterMinMaxInteger numFilter;
    final Handler myHandler = new Handler();

    long startTime;
    int lottoBallCounter = 0;
    final double jackpot = 50000000;

    //Arrays
    int[] lottoBet = new int[6], lottoResult = new int[6];
    EditText[] arrRandoms, arrBets;

    // ArrayLists
    ArrayList<Integer> listLottoBalls;
    ArrayList<String> listPlayers;
    ArrayList<int[]> listLottoBets;

    // Arrays During Deciding of Winners and Prizes
    int[] arrMatchCount;
    String[] arrMatchNumbers;
    String[] arrPrizes;

    // Display Dialogs for Player List and Winner List
    DialogWith2Buttons dialogPlayers, dialogWinners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ArrayList and Arrays
        listLottoBalls = new ArrayList<>();
        listPlayers = new ArrayList<>();
        listLottoBets = new ArrayList<>();
        generateLottoNumbers();

        // Initialize Filter
        numFilter = new InputFilterMinMaxInteger(1,58);

        // Initialize Components/Views
        txtName = findViewById(R.id.txtName);
        txtAmount = findViewById(R.id.txtAmount);
        txtBet1 = findViewById(R.id.txtBet1);
        txtBet2 = findViewById(R.id.txtBet2);
        txtBet3 = findViewById(R.id.txtBet3);
        txtBet4 = findViewById(R.id.txtBet4);
        txtBet5 = findViewById(R.id.txtBet5);
        txtBet6 = findViewById(R.id.txtBet6);
        txtRandom1 = findViewById(R.id.txtRandom1);
        txtRandom2 = findViewById(R.id.txtRandom2);
        txtRandom3 = findViewById(R.id.txtRandom3);
        txtRandom4 = findViewById(R.id.txtRandom4);
        txtRandom5 = findViewById(R.id.txtRandom5);
        txtRandom6 = findViewById(R.id.txtRandom6);
        btnPlaceBet = findViewById(R.id.btnPlaceBet);
        btnStart = findViewById(R.id.btnStart);
        btnShowBets = findViewById(R.id.btnShowBets);
        btnShowResults = findViewById(R.id.btnShowResults);
        lblResult = findViewById(R.id.lblResult);
        arrRandoms = new EditText[] {txtRandom1, txtRandom2, txtRandom3, txtRandom4, txtRandom5, txtRandom6};
        arrBets = new EditText[] {txtBet1, txtBet2, txtBet3, txtBet4, txtBet5, txtBet6};

        // Set Filters
        for (int i = 0; i < arrBets.length; i++) {
            arrBets[i].setFilters(new InputFilter[]{ numFilter });
            arrBets[i].setOnFocusChangeListener((v, hasFocus) -> {
                EditText et = (EditText) v;
                String input = et.getText().toString();
                if (!input.isEmpty() && !isUnique(Integer.parseInt(input))) {
                    et.setText("");
                }
                getLottoBets();
            });
        }

        // Set Action Listener to Buttons
        btnPlaceBet.setOnClickListener(v -> {
            for (int i = 0; i < arrBets.length; i++) {
                if(arrBets[i].hasFocus()){
                    arrBets[i].clearFocus();
                }
            }
            if(txtName.getText().toString().trim().isEmpty()){
                MsgBox("Name must not be empty.");
                return;
            }
            if(isUnique(0)){
                // Add LottoBets to ArrayList
                listLottoBets.add(lottoBet.clone());
                // Add Player to ArrayList
                listPlayers.add(txtName.getText().toString());
                // Other Effects
                txtName.setText("");
                for(EditText et: arrBets) et.setText("");
                for (int i = 0; i < arrBets.length; i++) {
                    arrBets[i].setText("");
                    lottoBet[i] = 0;
                }
            }else{
                MsgBox("One or more lotto numbers is/are 0. Choose from 1-58 only.");
            }
        });

        btnStart.setOnClickListener(v -> {
            // Clear first the lotto result
            if(lottoBallCounter == 0){
                for(EditText et: arrRandoms){
                    et.setText("");
                }
            }
            startTime = System.currentTimeMillis();
            btnStart.setEnabled(false);
            setRandomInteger(arrRandoms[lottoBallCounter]);
        });

        btnShowBets.setOnClickListener(v -> displayPlayers());
        btnShowResults.setOnClickListener(v -> displayWinners());

        // Instantiate Reusable Alert Dialogs
        dialogPlayers = new DialogWith2Buttons("List of Players", "OK", "Clear Bets", Randomizer.this) {
            @Override
            public void onPostiveClick() { }
            @Override
            public void onNegativeClick() {
                listPlayers.clear();
                listLottoBets.clear();
            }
        };
        dialogWinners = new DialogWith2Buttons("Lotto Results", "OK", "Clear Bets", Randomizer.this) {
            @Override
            public void onPostiveClick() {

            }

            @Override
            public void onNegativeClick() {
                listPlayers.clear();
                listLottoBets.clear();
            }
        };
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_randomizer;
    }

    @Override
    protected String getActivityName() {
        return "Activity 5";
    }

    private void getLottoBets(){
        lottoBet[0] = tryParse(txtBet1);
        lottoBet[1] = tryParse(txtBet2);
        lottoBet[2] = tryParse(txtBet3);
        lottoBet[3] = tryParse(txtBet4);
        lottoBet[4] = tryParse(txtBet5);
        lottoBet[5] = tryParse(txtBet6);
    }

    private void setRandomInteger(EditText et) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime < 1000) {
                    myHandler.postDelayed(this, 100);
                    int num = listLottoBalls.get(randomNumber(0,listLottoBalls.size()-1));
                    et.setText(num+"");
                }else{
                    myHandler.removeCallbacks(this);
                    removeFromLottoNumbers(et);
                    lottoBallCounter++;
                    if(!btnStart.isEnabled()){
                        processResult();
                    }
                }
            }
        };
        myHandler.post(runnable);
    }

    private void processResult(){
        btnStart.setEnabled(true);
        if(lottoBallCounter < arrRandoms.length){
            btnStart.performClick();
        }else{
            // Get Results
            lottoResult[0] = Integer.parseInt(txtRandom1.getText().toString());
            lottoResult[1] = Integer.parseInt(txtRandom2.getText().toString());
            lottoResult[2] = Integer.parseInt(txtRandom3.getText().toString());
            lottoResult[3] = Integer.parseInt(txtRandom4.getText().toString());
            lottoResult[4] = Integer.parseInt(txtRandom5.getText().toString());
            lottoResult[5] = Integer.parseInt(txtRandom6.getText().toString());

            // Initialize Arrays for Winners and Prizes
            arrMatchCount = new int[listPlayers.size()];
            arrMatchNumbers = new String[listPlayers.size()];
            arrPrizes = new String[listPlayers.size()];

            // Check each player if they won
            for(int i=0; i < listPlayers.size(); i++){
                int count = 0;
                String temp = "";
                for (int j = 0; j < listLottoBets.get(0).length; j++){
                    for(int k = 0; k < lottoResult.length; k++){
                        if(listLottoBets.get(i)[j] == lottoResult[k]){
                            count++;
                            arrMatchCount[i] = count;
                            temp+=listLottoBets.get(i)[j]+" ";
                        }
                    }
                }
                arrMatchNumbers[i] = count == 0 ? "" : String.format(" (%s)", temp.trim());
            }
            if(hasWinners()){
                getPrizes(6, 100);
                getPrizes(5, 50);
                getPrizes(4, 20);
                getPrizes(3, 0);
            }

            // Display Lotto Results
            btnShowResults.performClick();

            // Reset Everything After Lotto Draw
            lottoBallCounter = 0;
            generateLottoNumbers();
        }
    }

    private boolean hasWinners(){
        for (int i = 0; i < arrMatchCount.length; i++) {
            if(arrMatchCount[i] >= 3) return true;
        }
        return false;
    }

    private void getPrizes(int numMatches, double percent){
        // Check for Jackpot
        int ctr = 0;
        double prize = 0;
        String strIndexes="";
        for (int i = 0; i < arrMatchCount.length; i++) {
            if(arrMatchCount[i] == numMatches){
                ctr++;
                strIndexes += i+" ";
            }
        }
        if(ctr != 0){
            // Decide for Prize
            if(percent != 0){
                prize = jackpot * (percent/100) / ctr;
            }else{
                prize = 5000;
            }
            // Look for Winners of Prizes
            for(String str: strIndexes.split(" ")){
                int index = Integer.parseInt(str);
                arrPrizes[index] = String.format(" [%,3.2f]", prize);
            }
        }
    }

    private void generateLottoNumbers(){
        listLottoBalls.clear();
        for (int i = 1; i <= 58; i++){
            listLottoBalls.add(i);
        }
    }

    private void removeFromLottoNumbers(EditText et){
        int num = Integer.parseInt(et.getText().toString());
        for(int i=0; i < listLottoBalls.size(); i++){
            if(listLottoBalls.get(i).intValue() == num){
                listLottoBalls.remove(i);
                break;
            }
        }
    }

    private String createAnnouncement(){
        String message = "";
        if(arrMatchCount != null){
            for (int i = 0; i < listPlayers.size(); i++) {
                message += String.format("%s - %d%s%s%s\n",listPlayers.get(i), arrMatchCount[i], arrMatchCount[i] <= 1 ? " match": " matches", arrMatchNumbers[i], arrPrizes[i] == null? "" : arrPrizes[i]);
            }
        }
        return message;
    }

    private void displayPlayers(){
        String message = "";
        for(int i = 0; i < listPlayers.size(); i++){
            message += listPlayers.get(i) +" - "+Arrays.toString(listLottoBets.get(i))+"\n";
        }
        dialogPlayers.setMessage(message);
        dialogPlayers.openDialog();
    }

    private void displayWinners(){
        dialogWinners.setMessage(createAnnouncement());
        dialogWinners.openDialog();
    }

    private boolean isUnique(int num){
        for (int i = 0; i < lottoBet.length; i++) {
            if (lottoBet[i] == num) return false;
        }
        return true;
    }

    private int tryParse(EditText et){
        try {
            return Integer.parseInt(et.getText().toString());
        }catch (Exception e){
            return 0;
        }
    }
}