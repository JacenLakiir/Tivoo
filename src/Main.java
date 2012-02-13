import tivoo.TivooSystem;


public class Main
{
    public static void main(String[] args)
    {
        TivooSystem s = new TivooSystem();
        s.loadFile("data/dukecal.xml");
        s.filterByKeyword("Lemur");
        s.outputSummaryAndDetailsPages("data/summary.html", "data/details_dir");
    }
}
