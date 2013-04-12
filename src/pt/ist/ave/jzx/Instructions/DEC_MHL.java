package pt.ist.ave.jzx.Instructions;

public class DEC_MHL extends Instruction{

	public DEC_MHL(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		int work8 = _cpu.getM_memory().read8(_cpu.hl16());
		work8 = _cpu.dec8(work8);
		_cpu.getM_memory().write8(_cpu.hl16(), work8);
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 11;
	}

}
