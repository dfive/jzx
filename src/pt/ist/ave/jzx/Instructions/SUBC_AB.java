package pt.ist.ave.jzx.Instructions;

public class SUBC_AB extends Instruction {

	public SUBC_AB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sbc_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
