package pt.ist.ave.jzx.Instructions;

public class LD_ADE extends Instruction {

	public LD_ADE(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.de16()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
