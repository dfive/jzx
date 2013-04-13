package pt.ist.ave.jzx.Instructions;

public class XOR_D extends Instruction {

	public XOR_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.xor_a(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
