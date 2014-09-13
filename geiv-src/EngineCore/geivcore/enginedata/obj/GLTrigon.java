package geivcore.enginedata.obj;

public class GLTrigon extends SquareablePObj
{
	public GLTrigon(geivcore.R R, String HEXC, float Dx1,
			float Dy1,float Dx2,float Dy2,float Dx3,float Dy3) {
		super(R, HEXC, Dx1, Dy1, new float[]{Dx1,Dx2,Dx3},new float[]{Dy1,Dy2,Dy3});
	}
}
