import java.util.Calendar;

public class ProblemSolver {

    VRPInstance vrp;
    Solution sol;
    private double temperature;
    private double lambda;


    ProblemSolver(VRPInstance vrp,double temperature,double lambda){
        this.vrp=vrp;
        this.temperature=temperature;
        this.lambda=lambda;
        Solution.instance=vrp;

    }



    public void recuitSimuler() {

        Calendar now = Calendar.getInstance();
        System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));
        Solution  s = new Solution();

        main.controller.display(s.getSolutionInit(), 0, s.getEvaliation());

        sol = s;
        double solDistance = sol.getEvaliation();
        float solVehicule = sol.getNombreVehicules();
        double sDistance =s.getEvaliation();
        double s1Distance;
        double DE = 0;
        int etap =1;
        Solution s1;
        int stop=0;

        while (temperature > 0.5) {
            etap++;

            s1 = vrp.getAliatoireVoisin(s);
            s1Distance=s1.getEvaliation();

            DE = s1Distance - sDistance ;
            //System.out.println(DE+" "+temperature);

            if (DE <= 0) {
                s = s1;
                sDistance = s1Distance;
                if (solDistance > sDistance) {

                    sol = s;
                    //if(vrp.getIndex()!=3||Math.random()<0.1)
                    vrp.setProfit(1, vrp.getIndex());
                    solDistance = sDistance;
                    solVehicule = sol.getNombreVehicules();
                    /*System.out.println(solDistance+"  "+temperature);System.out.println("%%%%%%"+etap);System.out.println(sol.getSol());System.out.println(solDistance);System.out.println(solVehicule);//System.out.println("tempurature=" + temperature); System.out.println("energy=" + DE);*/
                    main.controller.display(sol.getSol(), 0, sol.getEvaliation());
                    // Calendar now = Calendar.getInstance(); System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));

                }

            } else if (Math.exp(-DE / temperature) > Math.random()) {
                s = s1;
                sDistance = s1Distance;
            }


            if(etap%1000==0)
                temperature *= lambda;


        }
        System.out.println();
        System.out.println(etap);
        System.out.println("*************************");
        System.out.println(sol);
        System.out.println(solDistance);
        System.out.println(solVehicule);
        main.controller.display(sol.getSol(), 1, solDistance);
        Calendar now1 = Calendar.getInstance();
        System.out.println(now1.get(Calendar.HOUR_OF_DAY) + ":" + now1.get(Calendar.MINUTE) + ":" + now1.get(Calendar.SECOND));
        System.out.println((now1.getTimeInMillis()-now.getTimeInMillis())/1000);

    }

}

