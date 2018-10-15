import java.util.ArrayList;


public class VRPInstance {
// ArrayList<ArrayList<Integer>>solution = new ArrayList<>(); ArrayList<ArrayList<Integer>>Instance = new ArrayList<>();
    protected int numVehicules;
    protected int capVehicule;
    protected int [][] clients;
    protected double [][] distances;
    // protected double  solDis;
    // protected int solVehicules;
    private double [] profit = new double[4];
    double sum=0;
    int index;
    // int test=0; int test1=0;

    public VRPInstance(int numVehicules, int capVehicule, int [][] clients, double [][] distances){
      this.numVehicules=numVehicules;
      this.capVehicule=capVehicule;
      this.clients=clients;
      this.distances=distances;
      for (int i=0;i<profit.length;i++) {
          profit[i] = 1;
          sum+=1;
      }




  }


   /* public void recuitSimuler(double temperature,double lambda){ ArrayList<ArrayList<Integer>> s=creeInstanceInit(); main.controller.display(s,0,0); ArrayList<ArrayList<Integer>> sol=new ArrayList<>(); for(int k=0;k<s.size();k++) sol.add((ArrayList<Integer>) s.get(k).clone()); double solDistance=calculerSolutionDistance(s); float solVehicule=s.size(); double DE=0; while(temperature>0.01){ int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1); Solution s1 =getAliatoireVoisin(s,randomNum); if(s1!=null){ DE=calculerSolutionDistance(s1)-calculerSolutionDistance(s); //System.out.println(DE+" "+temperature); if(DE<=0){ s=s1; if(calculerSolutionDistance(sol)>calculerSolutionDistance(s)){ sol=new ArrayList<>(); for(int k=0;k<s.size();k++) sol.add((ArrayList<Integer>) s.get(k).clone()); solDistance=calculerSolutionDistance(sol); solVehicule=sol.size(); System.out.println(sol); System.out.println(solDistance); System.out.println(solVehicule); System.out.println("tempurature="+temperature); System.out.println("energy="+DE); main.controller.display(sol,0,0); Calendar now = Calendar.getInstance(); System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)+":" +now.get(Calendar.SECOND)); } }else if(Math.exp(-DE/temperature)>Math.random()){ s=s1; } temperature*=lambda; } //System.out.println(s); } System.out.println(); System.out.println(); System.out.println("*************************"); System.out.println(sol); System.out.println(solDistance); System.out.println(solVehicule); main.controller.display(sol,1,solDistance); Calendar now = Calendar.getInstance(); System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)+":" +now.get(Calendar.SECOND)); }*/

    public Solution getAliatoireVoisin(Solution sol){
        double randomNum = Math.random();
        int i=0;
        double x=0;
        while(x/sum<randomNum){
            i++;
            x+=profit[i-1];
        }
        index=i-1;
        //System.out.println(randomNum);
        //System.out.println(i);
        //System.out.println(sum);
       //System.out.println(profit[0]+"+"+profit[1]+"+"+profit[2]+"+"+profit[3]);

        switch (i){
          case 1:
              java.util.Random random = new java.util.Random();


              int random_route = random.nextInt(sol.getSol().size());
              return permutationIntraRoute(sol,random_route);



          case 2:
              random = new java.util.Random();
              ArrayList<Integer> list = new ArrayList<>();
              for(int j=0;j<sol.getSol().size();j++)
                  list.add(j);
              random_route = list.get(random.nextInt(list.size()));
              list.remove(list.indexOf(random_route));
              if(list.size()>=1) {
                  int random_route1 = list.get(random.nextInt(list.size()));
                  return shiftEntreDeuxRoute(sol, random_route, random_route1);
              }else
                  return sol;

          case 3:
              random = new java.util.Random();

              random_route = random.nextInt(sol.getSol().size());
              return creeNewRoute(sol,random_route);

          case 4:
              random = new java.util.Random();

              random_route = random.nextInt(sol.getSol().size());
              return insertionMultipleIntraRoute(sol,random_route);
          default:
              return null;

      }



    }

    public Solution creeNewRoute(Solution sol, int index) {
        Solution newSol;
        Solution oldSol= (Solution) sol.clone();
        java.util.Random random = new java.util.Random();
        int random_client = random.nextInt(oldSol.getSol().get(index).size()-1)+1;
        ArrayList<Integer> newRoute=new ArrayList<>();


        newRoute.add(0);
        newRoute.add(oldSol.getSol().get(index).get(random_client));
        oldSol.getSol().add(newRoute);
        oldSol.getSol().get(index).remove(random_client);

        if(oldSol.getSol().get(index).size()<=1)
              oldSol.getSol().remove(index);


        newSol= (Solution) oldSol.clone();
        return newSol;


    }

    public Solution permutationIntraRoute(Solution sol,int index){
        java.util.Random random = new java.util.Random();
        Solution newSol;
        Solution oldSol= (Solution) sol.clone();
        ArrayList<Integer> list = new ArrayList<>();

        for(int j=1;j<oldSol.getSol().get(index).size();j++)
            list.add(j);

        if(list.size()>2) {

            int random_client1 = list.get(random.nextInt(list.size()));
            list.remove(list.indexOf(random_client1));
            int random_client2 = list.get(random.nextInt(list.size()));
            int c = oldSol.getSol().get(index).get(random_client1);
            oldSol.getSol().get(index).set(random_client1, oldSol.getSol().get(index).get(random_client2));
            oldSol.getSol().get(index).set(random_client2, c);
        }

        newSol= (Solution) oldSol.clone();
        return newSol;
    }

    public Solution insertionMultipleIntraRoute(Solution sol,int index){
        java.util.Random random = new java.util.Random();
        Solution newSol;
        Solution oldSol= (Solution) sol.clone();
        ArrayList<Integer>oldlist=oldSol.getSol().get(index);
        ArrayList<Integer>newlist;
        ArrayList<Integer>temp=new ArrayList<>();
        int l = random.nextInt(oldlist.size()-1)+1;
        int d = random.nextInt(oldlist.size()-l);
       // System.out.println(oldlist +"        1");
        //System.out.println(l);
        //System.out.println(d);

        for(int i=l;i<l+d;i++)
            temp.add(oldlist.get(i));



        for(int i=l+d-1;i>=l;i--)
            oldlist.remove(i);



        int pos = random.nextInt(oldlist.size()-1)+1;
        for(int i=pos;i<pos+d;i++)
            oldlist.add(temp.get(i-pos));

        //System.out.println(oldlist +"        2");
        newlist= (ArrayList<Integer>) oldlist.clone();
        oldSol.getSol().set(index,newlist);
        newSol = (Solution) oldSol.clone();
        //System.out.println(test+"/"+test1+"="+test*1.0/test1*1.0);

        return newSol;
    }

    public Solution shiftEntreDeuxRoute(Solution sol,int index ,int index1){
        Solution newSol;
        Solution oldSol= (Solution) sol.clone();
        ArrayList<Integer> route1 = oldSol.getSol().get(index);
        ArrayList<Integer> route2 = oldSol.getSol().get(index1);
        java.util.Random random = new java.util.Random();
        ArrayList<Integer> list = new ArrayList<>();


        for(int j=1;j<route1.size();j++)
            list.add(j);

        int random_client1 = list.get(random.nextInt(list.size()));
        java.util.Random random1 = new java.util.Random();
        ArrayList<Integer> list1 = new ArrayList<>();


        for(int j=1;j<route2.size();j++)
            list1.add(j);

        int random_position1 = list1.get(random1.nextInt(list1.size()));
        route2.add(random_position1,route1.get(random_client1));
        oldSol.getSol().set(index1,route2);
        route1.remove(random_client1);
        oldSol.getSol().set(index,route1);


        if(oldSol.getSol().get(index).size()<=1)
              oldSol.getSol().remove(index);


        newSol = (Solution) oldSol.clone();
        return newSol;



    }

    public int calulerNbrDemandes(ArrayList<Integer> route){
        int s=0;
        for(int i=1;i<route.size();i++)
            s+=clients[route.get(i)][2];

        return s;
    }

    public double calculerRouteDistance(ArrayList<Integer>route ){
        double s=0.;
        for(int i = 0 ;i<route.size()-1;i++){

            if(route.get(i)-route.get(i+1)<0) {

                s += distances[route.get(i)][route.get(i + 1)];
            }else {

                if (route.get(i) - route.get(i + 1) > 0)
                    s += distances[route.get(i + 1)][route.get(i)];
                else
                    s += 10000;
            }
        }
        s+=distances[0][route.get(route.size()-1)] +100 * Math.max(0,calulerNbrDemandes(route)-capVehicule);
        return s;
    }

    public double calculerSolutionDistance(ArrayList<ArrayList<Integer>>sol ){
        double s=0.;
        for (ArrayList<Integer> aSol : sol) {

            s += calculerRouteDistance(aSol);

        }
        return s+100 *Math.max(0,sol.size()-numVehicules);
    }

    public boolean isValide(ArrayList<ArrayList<Integer>>sol){
        int s=0;
        int s1=0;
        for (ArrayList<Integer> aSol : sol)
            for (Integer anASol : aSol) s += anASol;


        for(int i=0;i<clients.length;i++)
            s1+=i;

        return s==s1;
    }

// public double fonctionObjective(double distance,double nbrVehicule){ return distance+nbrVehicule; }

    public void setProfit(double x,int index) {
        this.profit[index]+=x;
        sum+=x;
    }

//    public void initProfit() { sum=0;for (int i=0;i<profit.length;i++) { profit[i] = 1;sum+=1; } }

    public int getIndex() {
        return index;
    }
}
