package pt.ist.ave.jzx.Instructions;

public class LDAHL extends Instruction {

	public LDAHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.hl16()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
