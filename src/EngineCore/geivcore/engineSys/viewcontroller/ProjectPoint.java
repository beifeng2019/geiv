package geivcore.engineSys.viewcontroller;

public class ProjectPoint
{
	public float Dx,Dy,Dz;
	public ProjectPoint(float Dx,float Dy,float Dz)
	{
		this.Dx = Dx;
		this.Dy = Dy;
		this.Dz = Dz;
	}
	public void printPP()
	{
		System.out.println(Dx+","+Dy +","+Dz);
	}
}
