package modelTris;
import java.util.Random;
/**
 *
 * @author Vittorio Privitera
 */
public class Model {
    private boolean inGioco;
    private boolean diffcolta;
    private String[] mossePc=new String[4];
    private String[] mosseUt=new String[5];
    private int[][] campoDigioco=new int[3][3];
    private int xPc;
    private int yPc;
    int contPc,contUt;
    Random r=new Random();
    public Model()
    {
        reset();
    }
    
    public boolean isInGioco() {
        return inGioco;
    }

    public boolean isDiffcolta() {
        return diffcolta;
    }

    public String[] getMosse() {
        return mossePc;
    }

    public int[][] getCampoDigioco() {
        return campoDigioco;
    }

    public int getxPc() {
        return xPc;
    }

    public int getyPc() {
        return yPc;
    }

    public String[] getMosseUt() {
        return mosseUt;
    }
    
    public boolean terminaPartita()
    {
        if(!this.inGioco)return false;
        this.inGioco=false;
        return true;
    }
    
    public boolean iniziaPartita(boolean diff)
    {
        if(this.inGioco)return false;
        reset();
        this.diffcolta=diff;
        this.inGioco=true;
        return true;
    }
    
    private void reset()
    {
        this.inGioco=false;
        this.diffcolta=true;
        this.contPc=0;
        this.contUt=0;
        for(int i=0;i<this.mossePc.length;i++)
        {
            this.mossePc[i]="0";
        }
        for(int i=0;i<this.mosseUt.length;i++)
        {
            this.mosseUt[i]="0";
        }
        for(int i=0;i<this.campoDigioco.length;i++)
        {
            for(int j=0;j<this.campoDigioco.length;j++)
            {
                this.campoDigioco[i][j]=0;
            }
        }
    }
    /*
    public void stampaTutto() {
        System.out.println("InGioco "+inGioco);
        System.out.println("Difficolta "+diffcolta);
        System.out.println("Cont: "+cont);
        System.out.println("");
        stampaMatrice();
        stampaVettore();
    }
    
    private void stampaVettore()
    {
        for(int i=0;i<mossePc.length;i++)
        {
            System.out.println(mossePc[i]);
        }
        System.out.println("");
    }
    
    public void stampaMatrice()
    {
        for(int i=0;i<this.campoDigioco.length;i++)
        {
            for(int j=0;j<this.campoDigioco.length;j++)
            {
                System.out.print(campoDigioco[i][j]+"\t");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    */
    public int gioco(int x,int y)
    {
        //mettere il doppio array per salvare le mosse
        if(!inGioco)return -4;  //-4
        if(campoDigioco[x][y]==0)
        {
            campoDigioco[x][y]=1;
            mosseUt[contUt]=x+","+y;
            contUt++;
        }
        else return -3;//-3
        if(verificaTris(1))return -2;
        if(grigliaPiena())return -5;
        if(diffcolta)
        {
            posRandom();  //va a fare le posizioni random
            if(verificaTris(-1))return 2;
        }
        else 
        {
            if(posRagionata(-1))return 0; //ha fatto tris "0"
            else if(posRagionata(1))return -1;  //-1
            else posRandom();
        }
        return -1;//-1
    }
    
    private boolean grigliaPiena() 
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(campoDigioco[i][j]==0)return false;
            }
        }
        return true;
    }
    
    private void posRandom()
    {
        int x,y;
        boolean sem=true;
        do
        {
           x=r.nextInt(3);
           y=r.nextInt(3);
           if(campoDigioco[x][y]==0)sem=false;
        }while(sem);
        campoDigioco[x][y]=-1;
        mossePc[contPc]=x+","+y;
        xPc=x;
        yPc=y;
        contPc++;
    }
    
    private boolean posRagionata(int valore)
    {
        int somma,campo[][]=campoDigioco;
        //mettere il doppio ciclo per scorrere la matrice
        for(int i=0;i<campo.length;i++)
        {
            somma=0;
            for(int j=0;j<campo.length;j++)
            {
                somma+=campo[i][j];
            }
                if(somma==2*valore) 
                {
                    for(int j=0;j<campo.length;j++)
                    {
                        if(campo[i][j]==0)
                        {
                            campo[i][j]=-1;
                            mossePc[contPc]=i+","+j;
                            contPc++;
                            xPc=i;
                            yPc=j;
                            return true;
                        }
                    }
                }
        }
      
        for(int i=0;i<campo.length;i++)
        {
            somma=0;
            for(int j=0;j<campo.length;j++)
            {
                somma+=campo[j][i];
            }
                if(somma==2*valore)
                {
                    for(int j=0;j<campo.length;j++)
                    {
                        if(campo[j][i]==0)
                        {
                            campo[j][i]=-1;
                            mossePc[contPc]=j+","+i;
                            contPc++;
                            xPc=j;
                            yPc=i;
                            return true;
                        }
                    }
                }
        }
        
        somma=campo[0][0]+campo[1][1]+campo[2][2];
        if(somma==2*valore)
        {
            for(int i=0;i<campo.length;i++)
            {
                if(campo[i][i]==0)
                {
                    campo[i][i]=-1;
                    mossePc[contPc]=i+","+i;
                    contPc++;
                    xPc=i;
                    yPc=i;
                    return true;
                }
            }
        }
        
        somma=campo[0][2]+campo[1][1]+campo[2][0];
        if(somma==2*valore)
        {
           for(int i=0;i<campo.length;i++)
           {
                if(campo[i][campo.length-i-1]==0)
                {
                    campo[i][campo.length-i-1]=-1;
                    mossePc[contPc]=i+","+(campo.length-i-1);
                    contPc++;
                    xPc=i;
                    yPc=campo.length-i-1;
                    return true;
                }
           }
        }
        return false;
    }
    
    private boolean verificaTris(int valore)
    {
        int somma,campo[][]=campoDigioco;
        for(int i=0;i<campo.length;i++)
        {
            somma=0;
            for(int j=0;j<campo.length;j++)
            {
                somma+=campo[i][j];
            }
            if(somma==3*valore)return true;
        }
        for(int i=0;i<campo.length;i++)
        {
            somma=0;
            for(int j=0;j<campo.length;j++)
            {
                somma+=campo[j][i];
            }
            if(somma==3*valore)return true;
        }
        somma=campo[0][0]+campo[1][1]+campo[2][2];
        if(somma==3*valore)return true;                        
        somma=campo[0][2]+campo[1][1]+campo[2][0];
        if(somma==3*valore)return true;
        return false;
    }
}