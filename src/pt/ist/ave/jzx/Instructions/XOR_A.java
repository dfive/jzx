package pt.ist.ave.jzx.Instructions;

public class XOR_A extends Instruction {

	public XOR_A(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
