package pt.ist.ave.jzx.Instructions;

public class XOR_B extends Instruction {

	public XOR_B(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
