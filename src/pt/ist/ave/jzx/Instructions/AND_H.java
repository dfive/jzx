package pt.ist.ave.jzx.Instructions;

public class AND_H extends Instruction {

	public AND_H(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.and_a(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
