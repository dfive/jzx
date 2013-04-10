package pt.ist.ave.jzx.Instructions;

public class EXAFAF extends Instruction {

	public EXAFAF(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work16;
		_cpu.storeFlags();
		work16 = _cpu.af16();
		_cpu.af16(_cpu.getM_af16alt());
		_cpu.setM_af16alt(work16);
		_cpu.retrieveFlags();
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
