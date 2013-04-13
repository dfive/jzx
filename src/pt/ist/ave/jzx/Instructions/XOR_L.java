package pt.ist.ave.jzx.Instructions;

public class XOR_L extends Instruction {

	public XOR_L(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
