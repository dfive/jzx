package pt.ist.ave.jzx.Instructions;

public class LD_CN extends Instruction {

	public LD_CN(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.inc16pc()));

	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 7;
	}

}
