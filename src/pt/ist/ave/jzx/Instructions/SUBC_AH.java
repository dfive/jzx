package pt.ist.ave.jzx.Instructions;

public class SUBC_AH extends Instruction {

	public SUBC_AH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sbc_a(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
