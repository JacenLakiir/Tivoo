import tivoo.TivooSystem;


public class Main
{
    public static void main(String[] args)
    {
        TivooSystem s = new TivooSystem();
        s.loadFile("input1.xml");
        s.filterByKeyword("Lemur");
        s.outputSummaryAndDetailsPages("output/summary.html", "output/details_dir");
    }
}
