package pt.ist.ave.jzx.Instructions;

public class SUBC_AE extends Instruction {

	public SUBC_AE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sbc_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
