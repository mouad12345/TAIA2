import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    public static VRPInstance instance ;
    private double  solDis;
    private int solVehicules;
    private ArrayList<ArrayList<Integer>> sol=new ArrayList<>();

    public Solution(){}



    public ArrayList<ArrayList<Integer>> getSolutionInit(){
        java.util.Random random = new java.util.Random();
        ArrayList<Integer> list = new ArrayList<>();

        for(int j=1;j<instance.clients.length;j++)
            list.add(j);


        ArrayList<ArrayList<Integer>>  ins =new ArrayList<>();
        ArrayList<Integer> route =new ArrayList<>();
        // int []clientsActive=new int[instance.clients.length];
        solVehicules=0;
        solDis=0;
        /* int rootDis=0;*/// int demandes =0;
        int d=1;
        route.add(0);
        ins.add(route);

        while(d <instance.clients.length){
           /* if(route.size()==0)  { route.add(0); clientsActive[0]=1; demandes=0; }*/
            int random_client = list.get(random.nextInt(list.size()));
            boolean add=false;
            int j=0;
            do {
                if(j>=ins.size()){
                    route = new ArrayList<>();
                    route.add(0);
                    ins.add(route);
                }

                if (instance.calulerNbrDemandes(ins.get(j)) + instance.clients[random_client][2] <= instance.capVehicule) {
                    ins.get(j).add(random_client);
                    list.remove(list.indexOf(random_client));


                    d++;
                    add=true;
                }
                j++;
            }while(!add);


        }

        solDis=instance.calculerSolutionDistance(ins);
        solVehicules=ins.size();
        System.out.println(ins);
        System.out.println(solDis);
        System.out.println(solVehicules);
        System.out.println(instance.isValide(ins));
        //getSolutionInit2();
        this.sol=ins;
        return ins;

    }

    public ArrayList<ArrayList<Integer>> getSolutionInit2(){
        ArrayList<ArrayList<Integer>> ins = new ArrayList<>();

        for(int j = 1;j<instance.clients.length;j++){
            ArrayList<Integer> route = new ArrayList<>();
            route.add(0);
            route.add(j);
            ins.add(route);
        }

        ArrayList<Double> gain;
        ArrayList<Integer> gainX;
        ArrayList<Integer> gainY;
        ArrayList<Integer> newRoute;
        ArrayList<Integer> newRoute2;
        boolean changer=true;
        int echeq=0;
        do {


            gain = new ArrayList<>();
            gainX = new ArrayList<>();
            gainY = new ArrayList<>();
            // newRoute = new ArrayList<>();
            // newRoute2 = new ArrayList<>();
            // System.out.println("gainsize="+gain.size());

            for (int i = 0; i < ins.size() - 1; i++) {
                for (int j = i + 1; j < ins.size(); j++) {
                    gainX.add(i);
                    gainY.add(j);

                    if (ins.get(i).get(1) < ins.get(j).get(ins.get(j).size() - 1)) {
                        //System.out.println("disij="+instance.distances[ins.get(i).get(1)][ins.get(j).get(ins.get(j).size() - 1)]);
                        gain.add(instance.distances[0][ins.get(i).get(1)] + instance.distances[0][ins.get(j).get(ins.get(j).size() - 1)] - (instance.distances[ins.get(i).get(1)][ins.get(j).get(ins.get(j).size() - 1)]));
                    } else {
                        //System.out.println("disji="+instance.distances[ins.get(j).get(ins.get(j).size() - 1)][ins.get(i).get(1)]);
                        gain.add(instance.distances[0][ins.get(i).get(1)] + instance.distances[0][ins.get(j).get(ins.get(j).size() - 1)] - (instance.distances[ins.get(j).get(ins.get(j).size() - 1)][ins.get(i).get(1)]));
                    }
                }
            }

            for (int i = ins.size() - 1; i > 1; i--) {
                for (int j = i - 1; j > 0; j--) {
                    gainX.add(i);
                    gainY.add(j);

                    if (ins.get(i).get(1) < ins.get(j).get(ins.get(j).size() - 1)) {
                        //System.out.println("disij="+instance.distances[ins.get(i).get(1)][ins.get(j).get(ins.get(j).size() - 1)]);
                        gain.add(instance.distances[0][ins.get(j).get(1)] + instance.distances[0][ins.get(i).get(ins.get(i).size() - 1)] - (instance.distances[ins.get(j).get(1)][ins.get(i).get(ins.get(i).size() - 1)]));
                    } else {
                        //System.out.println("disji="+instance.distances[ins.get(j).get(ins.get(j).size() - 1)][ins.get(i).get(1)]);
                        gain.add(instance.distances[0][ins.get(j).get(1)] + instance.distances[0][ins.get(i).get(ins.get(i).size() - 1)] - (instance.distances[ins.get(i).get(ins.get(i).size() - 1)][ins.get(j).get(1)]));
                    }
                }
            }

            //System.out.println("max="+Collections.max(gain)); System.out.println("size="+ins.size());

            gain.sort(Collections.reverseOrder());

            if (!changer) {

                for (int j = 0; j < echeq; j++) {
                    gain.remove(gain.get(0));
                }
                // System.out.println(gain);
            }

            int x = gain.indexOf(gain.get(0));
            newRoute = (ArrayList<Integer>) ins.get(gainY.get(x)).clone();
            newRoute2 = (ArrayList<Integer>) ins.get(gainX.get(x)).clone();
            Collections.reverse(newRoute);
            // System.out.println(x); System.out.println(newRoute); System.out.println(ins.get(gainX.get(x)).size());
            for (int k = 0; k < newRoute.size() - 1; k++)
                newRoute2.add(newRoute.get(k));


            // System.out.println(ins.get(gainY.get(x)).size()); System.out.println("index=" + gainY.get(x));
            if (instance.calulerNbrDemandes(newRoute2) <= instance.capVehicule) {
                ins.remove(ins.get(gainY.get(x)));
                ins.remove(ins.get(gainX.get(x)));
                ins.add(newRoute2);
                changer = true;
                echeq = 0;
            } else {
                // System.out.println(instance.calulerNbrDemandes(newRoute2));
                changer = false;
                echeq++;
            }


            // System.out.println("size="+ins.size());


        }while(gain.size()-echeq>0);
        System.out.println(ins);
        System.out.println(instance.calculerSolutionDistance(ins));
        System.out.println(ins.size());
        System.out.println(instance.isValide(ins));
        //System.out.println(isValide(ins));
        this.sol=ins;
        return ins;

    }

    public double getEvaliation(){
        solDis=instance.calculerSolutionDistance(sol);
        return solDis;
    }

    public int getNombreVehicules(){
        solVehicules=sol.size();
        return solVehicules;
    }

    public void setSolution(ArrayList<ArrayList<Integer>> sol) {
        this.sol = sol;
    }

    public Object clone()
    {
        Solution copy = new Solution();
        copy.sol=new ArrayList<>();
        for (int k = 0; k < sol.size(); k++)
             copy.sol.add((ArrayList<Integer>) sol.get(k).clone());

        //System.out.println(instance.isValide(copy.sol));
        copy.solDis=solDis;
        copy.solVehicules=solVehicules;

        return copy;
    }


    public ArrayList<ArrayList<Integer>> getSol() {
        return sol;
    }
}
