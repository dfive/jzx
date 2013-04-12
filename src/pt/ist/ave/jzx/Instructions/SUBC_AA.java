package pt.ist.ave.jzx.Instructions;

public class SUBC_AA extends Instruction {

	public SUBC_AA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sbc_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
